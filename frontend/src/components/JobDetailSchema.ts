import { computed, h } from 'vue'
import { useDebounceField } from '@/composables/useDebounceField'
import { useForm } from 'vee-validate'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { schemas } from '@/api/validations.ts'
import type { TargetAgent } from '@/types/job.ts'
import { useControlledField } from '@/composables/useControlledField.ts'
import { toTypedSchema } from '@vee-validate/zod'

/**
 * Requirements
 *
 * In CREATE mode:
 * - Save and Cancel buttons are visible
 * - Clicking Cancel: navigate back to the previous page
 * In READ mode:
 * - On initial READ mode entry, detail data is fetched and used to populate form fields
 * - All fields cannot be changed
 * - Edit and Delete buttons are visible
 * - Clicking Edit changes form mode to UPDATE
 * In UPDATE mode:
 * - Save and Cancel buttons are visible
 * - Clicking Cancel: reset all inputs to initial state
 *
 * Clicking Save opens a confirmation dialog. On confirm, triggers create or update API.
 * Clicking Delete opens a confirmation dialog. On confirm, triggers delete API and navigates to group list page
 * Save button is disabled for a while to prevent multiple click
 *
 * [Job Name]
 * - Required
 * - Must be 1–100 characters
 * - All characters allowed
 * - keyboard doesn't work after limit
 *
 * [Group Description]
 * - Optional
 * - Up to 1000 characters
 * - All characters allowed
 * - validation: error feedback after debounce when input remains invalid
 *
 * [Tags]
 * - Optional
 * - Max 10 tags
 * - Add button is only enabled when input is non-empty and valid
 * - After click add button, shows error if duplicated or exceeds 10 tags
 * - Tags are displayed in a list with individual remove buttons
 *
 * [Agents]
 * - Required
 * - Must have 1–100 entries
 * - "Add button" is enabled only when both IP and UserName are present and valid
 * - After click add button, Shows error if duplicated or exceeds 100 agents
 * - Agents are displayed in a list with connection status per row (Connected / Failed / Unknown)
 * - "Test All Connections" button triggers connection checks
 * - Each agent can be removed individually (except in READ mode) - confirm before proceed
 *
 * All error messages are cleared on user input to reduce friction
 * Save validates all fields; if any are invalid, feedback is shown
 */

export function useJobForm() {
  const { validateField, meta } = useForm({
    validationSchema: toTypedSchema(schemas.SaveJobReq.merge(schemas.TargetAgentReq)),
    initialValues: {
      jobName: '',
      jobDescription: '',
      jobType: 'SSH',
      active: true,
      targetAgents: [],
      agentGroupId: '',
      targetAgentType: 'AGENT',
      ip: '',
      userName: '',
      period: undefined,
      script: undefined,
    },
  })

  async function validateForm(): Promise<boolean> {
    return (
      await Promise.all([
        validateField('jobName'),
        validateField('jobDescription'),
        validateField('jobType'),
        validateField('active'),
        validateField('targetAgents'),
        validateField('period'),
        validateField('script'),
      ])
    ).every((v) => v.valid)
  }

  return { validateForm, meta }
}

export function useJobNameField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('jobName')
  return { input, inputError, validateInput }
}

export function useJobDescriptionField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('jobDescription')
  return { input, inputError, validateInput }
}

export function useJobTypeField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('jobType')
  const types = ['SSH'] as const
  return { input, inputError, validateInput, types }
}

export function useActiveField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<boolean>('active')
  return { input, inputError, validateInput }
}

export function useTargetAgentField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
} = useControlledField<TargetAgent[]>('targetAgents')

  const targetAgentTypeField = useTargetAgentTypeField()
  const ipField = useIpField()
  const userNameField = useUserNameField()
  const agentGroupIdField = useAgentGroupIdField()

  const add = () => {
    input.value.push({
      targetAgentType: targetAgentTypeField.input.value,
      agentGroupId: agentGroupIdField.input.value,
      ip: ipField.input.value,
      userName: userNameField.input.value,
    })
    ipField.input.value = ''
    userNameField.input.value = ''
  }

  const remove = (index: number) => {
    input.value.splice(index, 1)
  }

  const error = computed(() => {
    return inputError.value ?? ipField.inputError.value ?? userNameField.inputError.value
  })

  return {
    input,
    inputError,
    validateInput,
    add,
    remove,
    error,
    targetAgentTypeField,
    ipField,
    userNameField,
    agentGroupIdField,
  }
}

function useTargetAgentTypeField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<'AGENT' | 'GROUP'>('targetAgentType')
  const types = ['AGENT', 'GROUP'].map(it => ({
    label: it,
    value: it,
  }))
  return { input, inputError, validateInput, types }
}

function useIpField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('ip')
  return { input, inputError, validateInput }
}

function useUserNameField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('userName')
  return { input, inputError, validateInput }
}

function useAgentGroupIdField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('agentGroupId')
  return { input, inputError, validateInput }
}

export function usePeriodField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string | undefined>('period')
  return { input, inputError, validateInput }
}

export function useScriptField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string | undefined>('script')
  return { input, inputError, validateInput }
}

// ✅ ui

export const jobColumn = {
  agentGroup: () => ({ title: 'Agent Group', key: 'agentGroupId' }),
  ip: () => ({ title: 'IP', key: 'ip' }),
  userName: () => ({ title: 'User', key: 'userName' }),
  remove: (onClick: (index: number) => void) => ({
    title: 'Action',
    render(row: any, index: number) {
      return h(BaseIconButton, {
        size: 'small',
        bordered: false,
        icon: 'lucide:trash-2',
        onClick: () => onClick(index),
      })
    },
  }),
}
