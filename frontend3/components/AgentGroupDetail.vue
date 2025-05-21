<script setup lang="ts">
  import { useRouter } from 'vue-router'
  import { useFormMode } from '@/composables/useFormMode'
  import { ref, watch } from 'vue'
  import { DataMode } from '@/composables/useDataMode'
  import { useAsyncFn } from '@/composables/useAsyncFn'
  import { type Agent, type AgentGroup, defaultAgentGroup } from '~/types/agentGroup'
  import { agentGroupApi } from '~/api/api'
  import { testAllConnections } from '~/components/AgentGroupDetailSchema'
  import ConfirmDialog from '~/components/ui/ConfirmDialog.vue'
  import { getHashColor } from '~/util/hashColors'
  import type { TableColumn } from '#ui/components/Table.vue'
  import { UBadge, UButton } from '#components'

  const { agentGroupId, dataMode } = defineProps<{
    agentGroupId: string
    dataMode: DataMode
  }>()

  const router = useRouter()
  const overlay = useOverlay()

  const modal = overlay.create(ConfirmDialog)

  const updateFlag = ref(false)
  const { formMode, isCreateMode, isReadMode } = useFormMode({
    dataMode,
    updateFlag,
  })

  const fetchedAgentGroup = ref<AgentGroup>(defaultAgentGroup())
  const { isReady, execute: fetchAgentGroup } = useAsyncFn(
    async () => {
      fetchedAgentGroup.value = (await agentGroupApi.getAgentGroupById(agentGroupId)).data
    },
  )

  const { isLoading: testAllConnectionsLoading, execute: executeTestAllConnections } = useAsyncFn(
    async () => {
      await testAllConnections(fetchedAgentGroup.value.agents)
    },
  )


  watch(isReady, () => {
    if (isReady.value) {
      executeTestAllConnections()
    }
  })

  function pressUpdate() {
    updateFlag.value = true
  }

  function cancel() {
    if (isCreateMode.value) {
      router.back()
    } else {
      updateFlag.value = false
    }
  }

  async function save(agentGroupId: string) {
    if (isCreateMode.value) {
      await navigateTo({ name: 'agentGroupDetail', params: { agentGroupId } }, { replace: true })
    } else {
      updateFlag.value = false
      await fetchAgentGroup()
    }
  }

  async function pressDelete() {
    modal.open({ message: 'Are you sure you want to delete?', yes: deleteAgentGroup })
  }

  async function deleteAgentGroup() {
    await agentGroupApi.deleteAgentGroupById(agentGroupId)
    await navigateTo('/agentGroupList')
  }

  if (!isCreateMode.value) {
    fetchAgentGroup()
  }

  const columns: TableColumn<Agent>[] = [
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
  ]

</script>

<template>
  <UCard v-if="isReadMode">
    <template #header>
      <div class="flex justify-between">
        <h1 class="text-2xl font-bold">{{ fetchedAgentGroup.agentGroupName }}</h1>
        <div class="space-x-2">
          <UButton
            icon="lucide:pencil"
            color="neutral"
            variant="outline"
            @click="pressUpdate"
            data-testid="updateButton">Edit
          </UButton>
          <UButton
            icon="lucide:trash-2"
            color="neutral"
            variant="outline"
            @click="pressDelete" data-testid="deleteButton">
            Delete
          </UButton>
        </div>
      </div>
    </template>

    <section v-if="isReady" class="space-y-6">
      <!-- Agent Group Description -->
      <div>
        <h2 class="font-medium text-neutral mb-1">
          Group Description
        </h2>
        <div
          class="rounded border border-neutral-300 bg-gray-50 p-2 text-sm font-mono max-h-48 overflow-auto whitespace-pre-wrap">
          {{ fetchedAgentGroup.agentGroupDescription ?? '-' }}
        </div>
      </div>
      <!-- Tags -->
      <div>
        <h2 class="font-medium text-neutral mb-1">Tags</h2>
        <div class="space-x-2">
          <UBadge
            v-for="(tag, index) in fetchedAgentGroup.tags"
            :key="tag + index"
            :class="getHashColor(tag)"
            color="neutral"
            variant="subtle">
            {{ tag }}
          </UBadge>
        </div>
      </div>

      <!-- Agents -->
      <div>
        <div class="flex justify-between items-center mb-2">
          <h2 class="font-medium text-neutral">Agents</h2>
          <UButton
            icon="lucide:activity"
            color="neutral"
            variant="outline"
            label="Connection Test"
            @click="executeTestAllConnections" :loading="testAllConnectionsLoading">
          </UButton>
        </div>
        <UTable :data="fetchedAgentGroup.agents" :columns="columns" />
      </div>
    </section>
    <div v-else>
      <USkeleton text :repeat="1" style="width: 200px; height: 32px" />
      <UFormField label="Group Description">
        <USkeleton text :repeat="4" />
      </UFormField>
      <UFormField label="Tags">
        <div>
          <USkeleton v-for="i in 3" :key="i" :width="60" :height="24" />
        </div>
      </UFormField>
      <UFormField label="Agents">
        <USkeleton text :repeat="6" />
      </UFormField>
    </div>
  </UCard>
  <AgentGroupDetailEdit
    v-else
    :agent-group="fetchedAgentGroup"
    :form-mode="formMode"
    @cancel="cancel"
    @save="save"
  />
</template>

<style scoped>
</style>
