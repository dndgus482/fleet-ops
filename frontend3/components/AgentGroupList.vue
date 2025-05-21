<script setup lang="ts">
  import { ref } from 'vue'
  import type { AgentGroup } from '~/types/agentGroup.ts'
  import { getHashColor } from '~/util/hashColors'
  import { agentGroupApi } from '~/api/api'

  const agentGroups = ref<AgentGroup[]>([])

  async function pressSwitchActive(agentGroup: AgentGroup) {
    const res = await agentGroupApi.switchActiveAgentGroup(agentGroup.agentGroupId, { active: !agentGroup.active })
    agentGroup.active = res.data.active
  }

  async function pressCreate() {
    await navigateTo('/agentGroups/new')
  }

  async function goToDetail(agentGroupId: string) {
    await navigateTo(`/agentGroups/${agentGroupId}`)
  }

  async function fetchAgentGroups() {
    const response = await agentGroupApi.getAllAgentGroup()
    agentGroups.value = response.data
  }

  fetchAgentGroups()
</script>
<template>
  <UCard class="max-w-4xl mx-auto relative">
    <template #header>
      <div class="flex justify-between items-center">
        <h1 class="text-2xl font-bold">Agent Groups</h1>
        <UButton icon="i-heroicons-plus" variant="subtle" @click="pressCreate" label="New" />
      </div>
    </template>

    <ul>
      <li
        v-for="agentGroup in agentGroups"
        :key="agentGroup.agentGroupId"
        @click="goToDetail(agentGroup.agentGroupId)"
        class="rounded p-4 shadow hover:shadow-md cursor-pointer transition bg-gray-50 mb-3">
        <div class="p-1 flex items-center">
          <div class="font-medium text-gray-800 flex-2">
            {{ agentGroup.agentGroupName }}
          </div>
          <USwitch label="Active" color="success"
                   :model-value="agentGroup.active" @click.stop="pressSwitchActive(agentGroup)" />
        </div>
        <div class="p-1 flex items-center border-b border-default gap-2">
          <UBadge
            v-for="tag in agentGroup.tags"
            :key="tag"
            variant="subtle"
            color="neutral"
            :class="getHashColor(tag)">
            {{ tag }}
          </UBadge>
        </div>
        <div class="p-1 flex flex-wrap">
          <div v-for="agent in agentGroup.agents"
               :key="agent.userName + '@' + agent.ip"
               class="rounded text-xs text-gray-700">
            <UBadge icon="ci:dot-04-l" class="text-green-500" color="neutral" variant="soft">
              <span class="text-black">
                {{ agent.ip }} ({{ agent.userName }})
              </span>
            </UBadge>
          </div>
        </div>
      </li>
    </ul>
  </UCard>
</template>
<style scoped>
</style>
