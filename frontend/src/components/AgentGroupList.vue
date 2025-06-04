<script setup lang="ts">
import type { AgentGroup } from '@/types/agentGroup.ts'
import { agentGroupApi } from '@/api/api.ts'
import { useRouter } from 'vue-router'
import { useAsyncState } from '@vueuse/core'
import { useAsyncFn } from '@/composables/useAsyncFn.ts'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { NBadge, NPopover, NSwitch } from 'naive-ui'
import { h, reactive, watchEffect } from 'vue'
import { testAllConnections } from '@/components/AgentGroupDetailSchema.ts'
import AgentGroupListAgentConnections from '@/components/AgentGroupListAgentConnections.vue'

const router = useRouter()

const fetchAgentGroups = useAsyncState<AgentGroup[]>(async () => {
  const res = await agentGroupApi.getAllAgentGroup()
  return reactive(res.data)
}, [])

const switchActive = useAsyncFn(async (agentGroup: AgentGroup) => {
  const res = await agentGroupApi.switchActiveAgentGroup(
    agentGroup.agentGroupId,
    { active: !agentGroup.active },
  )
  agentGroup.active = res.data.active
})

const connectionTest = useAsyncFn(async () => {
  for (const agentGroup of fetchAgentGroups.state.value) {
    await testAllConnections(agentGroup.agents)
    agentGroup.connected = agentGroup.agents.every((agent) => agent.connected)
  }
})

watchEffect(() => {
  if (fetchAgentGroups.state.value) {
    connectionTest.execute()
  }
})

const pressCreate = async () => {
  await router.push({
    name: 'agentGroupDetail',
    params: { agentGroupId: 'new' },
  })
}

const goToDetail = async (agentGroupId: string) => {
  await router.push({ name: 'agentGroupDetail', params: { agentGroupId } })
}

const agentGroupListColumn = {
  active: () => ({
    title: 'Active',
    key: 'active',
    width: '10%',
    render(row: AgentGroup) {
      return h(NSwitch, {
        loading: switchActive.isLoading.value,
        value: row.active,
        'onUpdate:value': (val: boolean) => {
          switchActive.execute(row)
        },
        onClick: (e: MouseEvent) => {
          e.stopPropagation()
        },
      })
    },
  }),
  agentGroupName: () => ({
    title: 'Name',
    key: 'agentGroupName',
    width: '40%',
    render(row: AgentGroup) {
      return h(
        NPopover,
        { trigger: 'hover' },
        {
          trigger: () =>
            h(
              'div',
              {
                class: 'flex gap-2 items-center',
              },
              [
                h(NBadge, {
                  dot: true,
                  type: row.connected ? 'success' : 'error',
                }),
                row.agentGroupName,
              ],
            ),
          default: () =>
            h(AgentGroupListAgentConnections, {
              agents: row.agents,
            }),
        },
      )
    },
  }),
  agentGroupDescription: () => ({
    title: 'Description',
    key: 'agentGroupDescription',
    ellipsis: {
      tooltip: true,
    },
    width: '50%',
  }),
}

const rowProps = (row: AgentGroup) => {
  return {
    class: 'cursor-pointer',
    onClick: () => {
      goToDetail(row.agentGroupId)
    },
  }
}
</script>

<template>
  <n-card title="Agent Groups">
    <template #header-extra>
      <base-icon-button
        @click="pressCreate"
        icon="lucide:plus"
        type="primary"
        secondary
      >
        New
      </base-icon-button>
    </template>
    <n-data-table
      :columns="[
        agentGroupListColumn.active(),
        agentGroupListColumn.agentGroupName(),
        agentGroupListColumn.agentGroupDescription(),
      ]"
      :data="fetchAgentGroups.state.value"
      :row-props="rowProps"
      :bordered="false"
    >
    </n-data-table>
  </n-card>
</template>
