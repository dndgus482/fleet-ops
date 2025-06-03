<script setup lang="ts">
import { FormMode } from '@/composables/useFormMode'
import { type AgentGroup } from '@/types/agentGroup.ts'
import { getHashColor } from '@/util/hashColors.ts'
import { NButton, NTag, useDialog } from 'naive-ui'
import { agentGroupApi } from '@/api/api.ts'
import { dialogOptions } from '@/components/ui/dialogOptions.ts'
import {
  agentColumn,
  testAllConnections,
  useAgentField,
  useAgentGroupDescriptionField,
  useAgentGroupForm,
  useAgentGroupNameField,
  useTagField,
} from '@/components/AgentGroupDetailSchema.ts'
import type { SaveAgentGroupReq } from '@generated/api.ts'
import { useAsyncFn } from '@/composables/useAsyncFn.ts'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'

const { agentGroup, formMode } = defineProps<{
  agentGroup: AgentGroup
  formMode: FormMode
}>()

const emit = defineEmits<{
  (e: 'save', agentGroupId: string): void
  (e: 'cancel'): void
}>()

const isUpdateMode = formMode === FormMode.UPDATE

const dialog = useDialog()

const form = useAgentGroupForm()
const agentGroupNameField = useAgentGroupNameField()
const agentGroupDescriptionField = useAgentGroupDescriptionField()
const tagField = useTagField()
const agentField = useAgentField()

const pressCancel = async () => {
  emit('cancel')
}

const saveAgentGroup = useAsyncFn(async () => {
  const {
    data: { agentGroupId },
  } = isUpdateMode
    ? await agentGroupApi.updateAgentGroup(agentGroup.agentGroupId, toPayload())
    : await agentGroupApi.createAgentGroup(toPayload())

  emit('save', agentGroupId)
})

const pressSave = async () => {
  if (!(await form.validateForm())) return

  dialog.create({
    ...dialogOptions,
    type: 'success',
    title: 'Confirm Save',
    content: 'Are you sure you want to save?',
    negativeText: 'Cancel',
    positiveText: 'Save',
    onPositiveClick: () => saveAgentGroup.execute(),
  })
}

function loadField(agentGroup: AgentGroup) {
  agentGroupNameField.input.value = agentGroup.agentGroupName
  agentGroupDescriptionField.input.value = agentGroup.agentGroupDescription
  tagField.tags.value = agentGroup.tags
  agentField.agents.value = agentGroup.agents
}

function toPayload(): SaveAgentGroupReq {
  return {
    agentGroupName: agentGroupNameField.input.value,
    agentGroupDescription: agentGroupDescriptionField.input.value,
    tags: tagField.tags.value,
    agents: agentField.agents.value,
  }
}

loadField(agentGroup)
</script>

<template>
  <n-card>
    <n-form>
      <n-form-item
        label="Name"
        :feedback="agentGroupNameField.inputError.value"
        :validation-status="
          agentGroupNameField.inputError.value ? 'error' : undefined
        "
      >
        <n-input
          v-model:value="agentGroupNameField.input.value"
          show-count
          data-testid="agentGroupNameInput"
        />
      </n-form-item>
      <n-divider />
      <n-form-item
        label="Description"
        :feedback="agentGroupDescriptionField.inputError.value"
        :validation-status="
          agentGroupDescriptionField.inputError.value ? 'error' : undefined
        "
      >
        <n-input
          type="textarea"
          v-model:value="agentGroupDescriptionField.input.value"
          show-count
          data-testid="agentGroupDescriptionInput"
        />
      </n-form-item>
      <n-divider />
      <n-form-item
        label="Tags"
        :feedback="tagField.errorMessage.value"
        :validation-status="tagField.errorMessage.value ? 'error' : undefined"
      >
        <div class="flex flex-col gap-2">
          <div class="flex items-center gap-2">
            <n-input
              v-model:value="tagField.input.value"
              placeholder="Add tag"
              show-count
              data-testid="tagInput"
            />
            <base-icon-button
              @click="tagField.add"
              type="primary"
              :disabled="!tagField.isAddButtonEnabled.value"
              icon="lucide:plus"
            >
              Add
            </base-icon-button>
          </div>
          <div class="flex items-center gap-2">
            <n-tag
              v-for="(tag, index) in tagField.tags.value"
              :key="tag + index"
              :bordered="false"
              :color="{ color: getHashColor(tag) }"
              type="default"
              closable
              @close="tagField.remove(index)"
            >
              {{ tag }}
            </n-tag>
          </div>
        </div>
      </n-form-item>
      <n-divider />
      <div class="flex flex-col gap-2">
        <n-form-item
          label="Agents"
          :feedback="agentField.errorMessage.value"
          :validation-status="
            agentField.errorMessage.value ? 'error' : undefined
          "
        >
          <div class="flex items-center gap-2">
            <n-input
              v-model:value="agentField.ipInput.value"
              placeholder="IP"
              :status="agentField.ipInputError.value ? 'error' : undefined"
              show-count
              data-testid="ipInput"
            />
            <n-input
              v-model:value="agentField.userNameInput.value"
              placeholder="User"
              :status="
                agentField.userNameInputError.value ? 'error' : undefined
              "
              show-count
              data-testid="userNameInput"
            />
            <base-icon-button
              @click="agentField.add"
              type="primary"
              :disabled="!agentField.isAddButtonEnabled.value"
              icon="lucide:plus"
            >
              Add
            </base-icon-button>
          </div>
        </n-form-item>
      </div>
      <div class="flex flex-col gap-2">
        <div class="flex justify-end gap-2">
          <base-icon-button
            size="small"
            @click="testAllConnections(agentField.agents.value)"
            class="justify-end"
            icon="lucide:activity"
          >
            Connection Test
          </base-icon-button>
        </div>
        <n-data-table
          :columns="[
            agentColumn.logColumn(),
            agentColumn.ipColumn(),
            agentColumn.userNameColumn(),
            agentColumn.statusColumn(),
            agentColumn.removeColumn(agentField.remove),
          ]"
          :data="agentField.agents.value"
        />
      </div>
      <n-divider />
      <div class="flex justify-end gap-2">
        <n-button @click="pressCancel" data-testid="cancelButton">
          Cancel
        </n-button>
        <base-icon-button
          type="primary"
          @click="pressSave"
          :loading="form.meta.value.pending || saveAgentGroup.isLoading.value"
          icon="lucide:save"
          data-testid="saveButton"
        >
          Save
        </base-icon-button>
      </div>
    </n-form>
  </n-card>
</template>
