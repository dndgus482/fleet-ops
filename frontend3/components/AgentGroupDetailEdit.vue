<script setup lang="ts">
  import { useAsyncState } from '@vueuse/core'
  import type { Agent, AgentGroup } from '~/types/agentGroup'
  import ConfirmDialog from '~/components/ui/ConfirmDialog.vue'
  import {
    testAllConnections,
    useAgentField,
    useAgentGroupDescriptionField,
    useAgentGroupForm,
    useAgentGroupNameField,
    useTagField,
  } from '~/components/AgentGroupDetailSchema'
  import { agentGroupApi } from '~/api/api'
  import type { SaveAgentGroupReq } from '~/generated'
  import { getHashColor } from '~/util/hashColors'
  import type { TableColumn } from '#ui/components/Table.vue'
  import { UBadge, UButton } from '#components'

  const { agentGroup, formMode } = defineProps<{
    agentGroup: AgentGroup
    formMode: FormMode
  }>()

  const emit = defineEmits<{
    (e: 'save', agentGroupId: string): void
    (e: 'cancel'): void
  }>()

  const isUpdateMode = formMode === FormMode.UPDATE

  const overlay = useOverlay()
  const modal = overlay.create(ConfirmDialog)

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
    if (!await form.validateForm()) return

    modal.open({ message: 'Are you sure you want to save?', yes: saveAgentGroup })
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

  const columns: TableColumn<Agent>[] = [
    {
      id: 'expand',
      cell: ({ row }) => {
        if (!row.original.log) return null
        return h(UButton, {
          color: 'neutral',
          variant: 'ghost',
          icon: 'i-lucide-chevron-right',
          square: true,
          'aria-label': 'Expand',
          ui: {
            leadingIcon: [
              'transition-transform',
              row.getIsExpanded() ? 'duration-200 rotate-90' : '',
            ],
          },
          onClick: () => row.toggleExpanded(),
        })
      },
    },
    {
      accessorKey: 'ip',
      header: 'ip',
    },
    {
      accessorKey: 'userName',
      header: 'userName',
    },
    {
      header: 'status',
      cell: ({ row }) => {
        const label = (() => {
          if (row.original.connected === true) return 'Connected'
          if (row.original.connected === false) return 'Failed'
          return 'Loading'
        })()
        return h(
          UBadge,
          {
            color: (() => {
              if (row.original.connected === true) return 'success'
              if (row.original.connected === false) return 'error'
              return 'neutral'
            })(),
            label: label,
            variant: 'subtle',
            icon: row.original.connected == null ? 'lucide:loader' : '',
          },
        )
      },
    },
    {
      header: 'Action',
      cell: ({ row }) => {
        return h(
          UButton,
          {
            icon: 'lucide:trash-2',
            variant: 'ghost',
            color: 'neutral',
            onClick: () => agentField.remove(row.index),
          },
        )
      },
    },
  ]

  loadField(agentGroup)
</script>

<template>
  <UCard>
    <UForm
      class="space-y-6"
      :state="{}"
      @submit="pressSave"
    >
      <UFormField
        label="Name"
        :error="agentGroupNameField.inputError.value"
        required
      >
        <UInput
          v-model="agentGroupNameField.input.value"
          data-testid="agentGroupNameInput"
        />
      </UFormField>
      <UFormField
        label="Description"
        :error="agentGroupDescriptionField.inputError.value"
      >
        <UTextarea
          v-model="agentGroupDescriptionField.input.value"
          data-testid="agentGroupDescriptionInput"
        />
      </UFormField>
      <UFormField
        label="Tags"
        :error="tagField.errorMessage.value"
      >
        <div class="flex flex-col gap-2">
          <div class="flex gap-2">
            <UInput
              v-model="tagField.input.value"
              placeholder="Add tag"
              data-testid="tagInput"
            />
            <UButton
              label="Add"
              @click="tagField.add"
              :disabled="!tagField.isAddButtonEnabled.value"
              class="disabled:opacity-50"
              icon="lucide:plus"
              color="neutral"
              variant="subtle"
            />
          </div>
          <div class="flex gap-2">
            <UBadge
              v-for="(tag, index) in tagField.tags.value"
              :key="tag + index"
              :class="getHashColor(tag)"
              color="neutral"
              variant="subtle"
              trailing-icon="lucide:x"
              @click="tagField.remove(index)"
            >
              {{ tag }}
            </UBadge>
          </div>
        </div>
      </UFormField>
      <UFormField
        label="Agents"
        required
      >
        <div class="flex flex-col">
          <div class="flex gap-2">
            <UFormField
              label="IP"
              class="text-sm"
              :error="agentField.ipInputError.value"
              required
              data-testid="agentFormItem"
            >
              <UInput
                v-model="agentField.ipInput.value"
              />
            </UFormField>
            <UFormField
              label="User Name"
              :error="agentField.userNameInputError.value"
              required
              data-testid="agentFormItem"
            >
              <UInput
                v-model="agentField.userNameInput.value"
              />
            </UFormField>

            <UFormField
              :error="agentField.errorMessage.value"
            >
              <template #label>
                <span class="invisible">Add</span>
              </template>
              <UButton
                label="Add"
                @click="agentField.add"
                :disabled="!agentField.isAddButtonEnabled.value"
                class="disabled:opacity-50"
                icon="lucide:plus"
                color="neutral"
                variant="subtle"
              />
            </UFormField>
          </div>
          <div class="flex justify-end">
            <UButton
              class="justify-end"
              label="Connection Test"
              @click="testAllConnections(agentField.agents.value)"
              variant="subtle"
              color="neutral"
              icon="lucide:activity"
            />
          </div>
          <UTable
            :data="agentField.agents.value ?? []"
            :columns="columns"
          >
            <template #expanded="{ row }">
              <pre>{{ row.original.log }}</pre>
            </template>
          </UTable>
        </div>
      </UFormField>
      <UFormField>
        <div class="flex justify-end gap-2">
          <UButton
            @click="pressCancel"
            data-testid="cancelButton"
            label="Cancel"
            variant="subtle"
            color="neutral"
          />
          <UButton
            type="submit"
            color="secondary"
            data-testid="saveButton"
            label="Save"
            variant="subtle"
            icon="lucide:save"
            :loading="form.meta.value.pending || isLoading"
          />
        </div>
      </UFormField>
    </UForm>
  </UCard>
</template>
