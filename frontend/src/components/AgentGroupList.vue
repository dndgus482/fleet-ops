<script setup lang="ts">
  import { onMounted, ref } from 'vue'
  import type { AgentGroup } from '@/types/agentGroup.ts'
  import { agentGroupApi } from '@/api/api.ts'
  import { getHashColor } from '@/util/hashColors.ts'
  import { useRouter } from 'vue-router'

  const router = useRouter()
  const agentGroups = ref<AgentGroup[]>([])

  async function pressSwitchActive(agentGroup: AgentGroup) {
    const res = await agentGroupApi.switchActiveAgentGroup(agentGroup.agentGroupId, { active: !agentGroup.active })
    agentGroup.active = res.data.active
  }

  async function pressCreate() {
    await router.push({ name: 'agentGroupDetail', params: { agentGroupId: 'new' } })
  }

  async function goToDetail(agentGroupId: string) {
    await router.push({ name: 'agentGroupDetail', params: { agentGroupId } })
  }

  async function fetchAgentGroups() {
    const response = await agentGroupApi.getAllAgentGroup()
    agentGroups.value = response.data
  }

  onMounted(fetchAgentGroups)
</script>
<template>
  <div class="p-6 max-w-4xl mx-auto bg-white shadow-lg rounded-lg relative">
    <h1 class="text-2xl font-bold mb-4">Agent Groups</h1>
    <button @click="pressCreate"
            class="absolute top-6 right-6 p-2 rounded-full shadow-lg hover:bg-gray-200 transition-transform transform hover:scale-110 flex items-center justify-center border border-gray-300">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2"
           stroke="currentColor" class="w-6 h-6 text-gray-600">
        <path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4" />
      </svg>
    </button>
    <div class="w-full mt-4">
      <div class="grid grid-cols-3 text-gray-500 text-sm font-semibold pb-2 border-b text-left">
        <div class="px-2 flex items-center h-6">Active</div>
        <div class="px-2">Agent Group Name</div>
        <div class="px-2">Description</div>
      </div>
      <ul class="divide-y divide-gray-100">
        <li v-for="agentGroup in agentGroups" :key="agentGroup.agentGroupId"
            @click="goToDetail(agentGroup.agentGroupId)"
            class="bg-gray-50 rounded-lg p-4 mb-3 shadow hover:shadow-md transition cursor-pointer">
          <div class="grid grid-cols-3 items-center text-left border-b border-gray-200 pb-3 mb-3">
            <div class="px-2 flex items-center h-6">
              <div @click.stop="pressSwitchActive(agentGroup)"
                   class="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                <input type="checkbox" :checked="agentGroup.active" class="hidden" />
                <label
                  :class="[
                    'block overflow-hidden h-6 rounded-full cursor-pointer',
                    agentGroup.active ? 'bg-green-500' : 'bg-gray-300'
                  ]">
                  <span
                    :class="[
                      'dot absolute left-1 top-1 bg-white w-4 h-4 rounded-full transition',
                      agentGroup.active ? 'translate-x-4' : ''
                    ]">
                  </span>
                </label>
              </div>
            </div>
            <div class="px-2 font-medium text-gray-800">{{ agentGroup.agentGroupName }}</div>
            <div class="px-2 text-gray-600">{{ agentGroup.agentGroupDescription }}</div>
          </div>
          <div class="mt-3 px-2 py-3 bg-gray-100 rounded">
            <div class="mb-4">
              <div class="flex flex-wrap gap-2">
                <span
                  v-for="tag in agentGroup.tags"
                  :key="tag"
                  :class="['text-xs font-semibold text-gray-800 px-2 py-1 rounded', getHashColor(tag)]">
                  {{ tag }}
                </span>
              </div>
            </div>
            <div>
              <div class="flex flex-wrap gap-2">
                <span
                  v-for="agent in agentGroup.agents"
                  :key="agent.userName + '@' + agent.ip"
                  class="bg-white border border-gray-300 rounded px-2 py-1 text-xs text-gray-700">
                  {{ agent.userName }}@{{ agent.ip }}
                </span>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<style scoped>
</style>
