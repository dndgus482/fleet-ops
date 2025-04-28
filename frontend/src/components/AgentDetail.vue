<script setup lang="ts">
import { useRoute } from 'vue-router'
import { ref, useTemplateRef } from 'vue'
import ConfirmDialog from '@/components/ui/ConfirmDialog.vue'

import { type Agent } from '@/types/agentGroup.ts'
import { agentGroupApi } from '@/api/api.ts'


const route = useRoute()
const ip = String(route.params.ip)
const userName = String(route.params.userName)

const agentConnectionDialog = useTemplateRef('agentConnectionDialog')

const currentAgent = ref<Agent>({
  ip: ip,
  userName: userName,
})

async function testConnection() {
  const agentConnectionRes = (await agentGroupApi.agentConnectionTest([currentAgent.value])).data[0]
  if (agentConnectionRes.connected) {
    if (!agentConnectionDialog.value) {
      return
    }
    await agentConnectionDialog.value.reveal()
  }
}

</script>

<template>
  <div class="p-6 max-w-xl mx-auto bg-white shadow-lg rounded-xl">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold">Agent</h2>
    </div>

    <div class="space-y-3">
      <label class="block">IP</label>
      <input :disabled="true" v-model="currentAgent.ip" class="input-field" />

      <label class="block">UserName</label>
      <input :disabled="true" v-model="currentAgent.userName" class="input-field" />

      <button
        @click="testConnection"
        class="text-xs px-2 py-0.5 rounded border border-gray-300 text-gray-600 hover:bg-gray-100"
      >
        연결 테스트
      </button>
    </div>

    <ConfirmDialog ref="agentConnectionDialog" message="Some agents not connected" />
  </div>
</template>

<style scoped>
.input-field {
  @apply w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500;
}
</style>
