import { computed, h, watch } from 'vue'
import { agentGroupApi } from '@/api/api.ts'
import type { Agent } from '@/types/agentGroup.ts'
import z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useDebounceField } from '@/composables/useDebounceField'
import { useControlledField } from '@/composables/useControlledField'
import { useValidation } from '@/composables/useValidation'
import { useForm } from 'vee-validate'
import { NTag } from 'naive-ui'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'

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
 * - Clicking Cancel: reset all inputs to initial state and re-run connection tests
 *
 * Clicking Save opens a confirmation dialog. On confirm, triggers create or update API.
 * Clicking Delete opens a confirmation dialog. On confirm, triggers delete API and navigates to group list page
 * Save button is disabled for a while to prevent multiple click
 *
 * [Group Name]
 * - Required
 * - Must be 1–100 characters
 * - All characters allowed
 * - validation: error feedback after debounce when input remains invalid
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

// ✅ validations

export const agentGroupValidationSchema = toTypedSchema(
  z.object({
    agentGroupName: z
      .string()
      .min(1, 'Group name is required')
      .max(100, 'Group name must be 100 characters or fewer'),

    agentGroupDescription: z
      .string()
      .max(1000, 'Description must be 1000 characters or fewer')
      .optional(),

    tagInput: z
      .string()
      .max(100, 'Tag must be 100 characters or fewer')
      .optional(),

    tags: z.array(z.string()).max(10, 'Maximum 10 tags allowed').optional(),

    ipInput: z
      // optional() doesn't work with ip()
      .union([
        z.string().ip({ message: 'Invalid IP address format' }),
        z.undefined(),
        z.literal(''),
      ]),

    userNameInput: z
      .string()
      .min(0)
      .max(100, 'User name must be 100 characters or fewer'),

    agents: z
      .array(z.any())
      .min(1, 'Please add at least one agent')
      .max(100, 'Maximum 100 agents allowed'),
  }),
)

function _validateAddTag(tags: string[], tag: string): string | undefined {
  if (!tag) return 'Please enter at least one character to add a tag'
  if (tags.length >= 10) return 'Maximum 10 tags allowed'
  if (tags.includes(tag)) return 'Tag already exists'
  return undefined
}

function _validateAddAgent(
  agents: Agent[],
  ip: string,
  userName: string,
): string | undefined {
  if (!ip || !userName) return 'ip and userName are required'
  if (agents.length >= 100) return 'Maximum 100 agents allowed'
  if (
    agents.length > 0 &&
    agents.some((agent) => agent.ip === ip && agent.userName === userName)
  )
    return 'Agent already exists'
  return undefined
}

// ✅ functions
export async function testAllConnections(agents: Agent[]) {
  const agentConnectionRes = (await agentGroupApi.agentConnectionTest(agents))
    .data
  for (const result of agentConnectionRes) {
    const target = agents.find(
      (it) => it.ip === result.ip && it.userName === result.userName,
    )
    if (target) {
      target.connected = result.connected
      target.log = result.log
    }
  }
}

// ✅ composable

export function useAgentGroupForm() {
  const { validateField, meta } = useForm({
    validationSchema: agentGroupValidationSchema,
    initialValues: {
      agentGroupName: '',
      agentGroupDescription: '',
      tagInput: '',
      tags: [],
      ipInput: '',
      userNameInput: '',
      agents: [],
    },
  })

  async function validateForm(): Promise<boolean> {
    return (
      await Promise.all([
        validateField('agentGroupName'),
        validateField('agentGroupDescription'),
        validateField('tags'),
        validateField('agents'),
      ])
    ).every((v) => v.valid)
  }

  return { validateForm, meta }
}

export function useAgentGroupNameField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('agentGroupName')
  return { input, inputError, validateInput }
}

export function useAgentGroupDescriptionField() {
  const {
    value: input,
    errorMessage: inputError,
    validate: validateInput,
  } = useDebounceField<string>('agentGroupDescription')
  return { input, inputError, validateInput }
}

export function useTagField() {
  const { value: input, errorMessage: inputError } =
    useDebounceField<string>('tagInput')

  const { value: tags, errorMessage: tagsError } =
    useControlledField<string[]>('tags')

  const { errorMessage: addTagError, validate: validateAddTag } = useValidation(
    () => {
      return _validateAddTag(tags.value, input.value)
    },
  )

  const errorMessage = computed(() => {
    return tagsError.value ?? addTagError.value ?? inputError.value
  })

  const isAddButtonEnabled = computed(() => {
    return input.value.length > 0 && !inputError.value
  })

  function add() {
    input.value = input.value.trim()
    if (!validateAddTag()) {
      return
    }
    tags.value.push(input.value)
    input.value = ''
  }

  function remove(index: number) {
    tags.value.splice(index, 1)
  }

  watch(input, () => {
    if (addTagError.value) {
      addTagError.value = undefined
    }
  })

  return { input, errorMessage, tags, isAddButtonEnabled, add, remove }
}

export function useAgentField() {
  const {
    value: ipInput,
    errorMessage: ipInputError,
    validate: validateIpInput,
  } = useDebounceField<string>('ipInput')
  const {
    value: userNameInput,
    errorMessage: userNameInputError,
    validate: validateUserNameInput,
  } = useDebounceField<string>('userNameInput')

  const { value: agents, errorMessage: agentError } =
    useControlledField<Agent[]>('agents')

  const { errorMessage: addAgentError, validate: validateAddAgent } =
    useValidation(() => {
      return _validateAddAgent(agents.value, ipInput.value, userNameInput.value)
    })

  const errorMessage = computed(() => {
    return (
      agentError.value ??
      addAgentError.value ??
      ipInputError.value ??
      userNameInputError.value
    )
  })

  const isAddButtonEnabled = computed(() => {
    return ipInput.value && userNameInput.value
  })

  async function add() {
    userNameInput.value = userNameInput.value.trim()
    ipInput.value = ipInput.value.trim()
    if (
      !(await validateIpInput()).valid ||
      !(await validateUserNameInput()).valid
    ) {
      return
    }
    if (!validateAddAgent()) {
      return
    }
    const agent = {
      ip: ipInput.value,
      userName: userNameInput.value,
    }
    agents.value.push(agent)
    ipInput.value = ''
    userNameInput.value = ''
    await testAllConnections()
  }

  function remove(index: number) {
    agents.value.splice(index, 1)
  }

  async function testAllConnections() {
    const agentConnectionRes = (
      await agentGroupApi.agentConnectionTest(agents.value)
    ).data
    for (const result of agentConnectionRes) {
      const target = agents.value.find(
        (it) => it.ip === result.ip && it.userName === result.userName,
      )
      if (target) {
        target.connected = result.connected
        target.log = result.log
      }
    }
  }

  watch([ipInput, userNameInput], () => {
    if (addAgentError.value) {
      addAgentError.value = undefined
    }
    if (agentError.value) {
      agentError.value = undefined
    }
  })

  return {
    ipInput,
    ipInputError,
    userNameInput,
    userNameInputError,
    agents,
    errorMessage,
    isAddButtonEnabled,
    add,
    remove,
    testAllConnections,
  }
}

// ✅ ui

export const agentColumn = {
  logColumn: () => ({
    type: 'expand',
    expandable: (row: any) => row.log,
    renderExpand: (row: any) => row.log,
  }),
  ipColumn: () => ({ title: 'IP', key: 'ip' }),
  userNameColumn: () => ({ title: 'User', key: 'userName' }),
  statusColumn: () => ({
    title: 'Status',
    key: 'connected',
    render(row: any) {
      return h(
        NTag,
        {
          type: row.connected ? 'success' : 'error',
          size: 'small',
        },
        {
          default: () => {
            if (row.connected === true) return 'Connected'
            if (row.connected === false) return 'Failed'
            return 'Unknown'
          },
        },
      )
    },
  }),
  removeColumn: (onClick: (index: number) => void) => ({
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
