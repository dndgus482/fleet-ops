<script setup lang="ts">
  import { useRouter } from 'vue-router'
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
  import { useAsyncState } from '@vueuse/core'
  import type { SaveAgentGroupReq } from '@generated/api.ts'

  const { agentGroup, formMode } = defineProps<{
    agentGroup: AgentGroup
    formMode: FormMode
  }>()

  const emit = defineEmits<{
    (e: 'save', agentGroupId: string): void
    (e: 'cancel'): void
  }>()

  const isUpdateMode = formMode === FormMode.UPDATE

  const router = useRouter()
  const dialog = useDialog()

  const form = useAgentGroupForm()
  const agentGroupNameField = useAgentGroupNameField()
  const agentGroupDescriptionField = useAgentGroupDescriptionField()
  const tagField = useTagField()
  const agentField = useAgentField()

  const pressCancel = async () => {
    emit('cancel')
  }

  const { isLoading, execute: saveAgentGroup } = useAsyncState<void>(
    async () => {
      const { data: { agentGroupId } } = isUpdateMode
        ? await agentGroupApi.updateAgentGroup(agentGroup.agentGroupId, toPayload())
        : await agentGroupApi.createAgentGroup(toPayload())

      emit('save', agentGroupId)
    },
    undefined,
    { immediate: false },
  )

  const pressSave = async () => {
    if (!form.validateForm()) return

    dialog.create({
      ...dialogOptions,
      type: 'success',
      title: 'Confirm Save',
      content: 'Are you sure you want to save?',
      negativeText: 'Cancel',
      positiveText: 'Save',
      onPositiveClick: () => saveAgentGroup(),
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
  <n-card class="w-full max-w-xl mx-auto">
    <n-form>
      <n-form-item
        label="Name"
        :feedback="agentGroupNameField.inputError.value"
        :validation-status="agentGroupNameField.inputError.value ? 'error' : undefined"
      >
        <n-input v-model:value="agentGroupNameField.input.value"
                 data-testid="agentGroupNameInput" />
      </n-form-item>
      <n-form-item label="Description" :feedback="agentGroupDescriptionField.inputError.value"
                   :validation-status="agentGroupDescriptionField.inputError.value ? 'error' : undefined">
        <n-input
          type="textarea"
          v-model:value="agentGroupDescriptionField.input.value"
          data-testid="agentGroupDescriptionInput"
        />
      </n-form-item>
      <n-form-item label="Tags" :feedback="tagField.errorMessage.value"
                   :validation-status="tagField.errorMessage.value ? 'error' : undefined">
        <n-space vertical size="small" style="width: 100%">
          <n-space>
            <n-input
              v-model:value="tagField.input.value"
              placeholder="Add tag"
              style="width: 200px"
              data-testid="tagInput"
            />
            <n-button @click="tagField.add" type="primary"
                      :disabled="!tagField.isAddButtonEnabled.value">
              Add
            </n-button>
          </n-space>
          <n-space wrap>
            <n-tag
              v-for="(tag, index) in tagField.tags.value"
              :key="tag + index"
              :bordered="false"
              :style="{ backgroundColor: getHashColor(tag) }"
              type="default"
              @close="tagField.remove(index)"
            >
              {{ tag }}
            </n-tag>
          </n-space>
        </n-space>
      </n-form-item>
      <n-form-item label="Agents" :feedback="agentField.errorMessage.value" data-testid="agentFormItem">
        <n-space vertical class="w-full">
          <n-space
            align="center">
            <n-input
              v-model:value="agentField.ipInput.value"
              placeholder="IP"
              style="width: 160px"
              :status="agentField.ipInputError.value ? 'error' : undefined"
            />
            <n-input
              v-model:value="agentField.userNameInput.value"
              placeholder="User"
              style="width: 160px"
              :status="agentField.userNameInputError.value ? 'error' : undefined"
            />
            <n-button
              @click="agentField.add"
              type="primary"
              :disabled="!agentField.isAddButtonEnabled.value"
            >
              Add
            </n-button>
          </n-space>
          <div class="flex justify-end">
            <n-button size="small" @click="testAllConnections(agentField.agents.value)" class="justify-end">
              Connection Test
            </n-button>
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
        </n-space>
      </n-form-item>
      <n-form-item>
        <n-space justify="end">
          <n-button
            @click="pressCancel"
            data-testid="cancelButton"
          >
            Cancel
          </n-button>
          <n-button
            type="primary"
            @click="pressSave"
            data-testid="saveButton"
            :loading="form.meta.value.pending || isLoading"
          >
            Save
          </n-button>
        </n-space>
      </n-form-item>
    </n-form>
  </n-card>
</template>

<style scoped>
</style>
