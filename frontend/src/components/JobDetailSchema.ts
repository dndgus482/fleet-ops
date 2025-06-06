import { computed, h, watch } from 'vue'
import { agentGroupApi } from '@/api/api.ts'
import type { Agent } from '@/types/agentGroup.ts'
import z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useDebounceField } from '@/composables/useDebounceField'
import { useControlledField } from '@/composables/useControlledField'
import { useValidation } from '@/composables/useValidation'
import { useForm } from 'vee-validate'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { NTag } from 'naive-ui'

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
