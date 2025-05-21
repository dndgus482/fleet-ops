<script setup lang="ts">
  import type { Agent } from '@/types/agentGroup.ts'
  import { ref } from 'vue'
  import { useAgentConnection } from '@/components/AgentDetailSchema.js'

  const { ip, userName } = defineProps<{
    ip: string, userName: string,
  }>()

  const fetchedAgent = ref<Agent>({
    ip,
    userName,
  })
  const connection = useAgentConnection(fetchedAgent.value)
</script>

<template>
  <n-card class="max-w-xl mx-auto" title="Agent" data-testid="agent-card">
    <n-space vertical :size="20">
      <n-descriptions
        label-placement="left"
        :column="1"
        bordered
        :label-style="{ whiteSpace: 'nowrap', width: 0 }"
        data-testid="agent-descriptions"
      >
        <n-descriptions-item label="IP">
          <span data-testid="agent-ip">{{ fetchedAgent.ip }}</span>
        </n-descriptions-item>
        <n-descriptions-item label="User Name">
          <span data-testid="agent-userName">{{ fetchedAgent.userName }}</span>
        </n-descriptions-item>
      </n-descriptions>

      <n-space align="center" data-testid="connection-control">
        <n-button
          data-testid="test-connection-btn"
          @click="connection.execute"
          strong
          secondary
          round
          type="info"
          :loading="connection.isLoading.value"
        >
          Test Connection
        </n-button>

        <div
          v-if="connection.state.value"
          :class="[
            'text-sm',
            connection.statusText.value === 'Success' ? 'text-green-500' :
            connection.statusText.value === 'Failed' ? 'text-red-500' : 'text-gray-500'
          ]"
          data-testid="connection-status"
        >
          {{ connection.statusText }}
        </div>
      </n-space>

      <n-card embedded v-if="connection.state.value" data-testid="log-card">
        <n-space
          justify="space-between"
          align="center"
          @click="connection.isLogVisible.value = !connection.isLogVisible.value"
          data-testid="log-toggle"
        >
          <span class="text-sm font-medium text-red-500">Error Log</span>
          <n-icon size="14">
            {{ connection.isLogVisible.value ? '▼' : '▶︎' }}
          </n-icon>
        </n-space>

        <n-divider v-show="connection.isLogVisible.value" />

        <n-scrollbar
          v-if="connection.isLogVisible.value"
          style="max-height: 12rem"
        >
          <n-code
            data-testid="error-log"
            :code="connection.state.value"
            class="text-xs"
            word-wrap
            show-line-numbers
            style="max-height: 12rem; overflow: auto;"
          />
        </n-scrollbar>
      </n-card>
    </n-space>
  </n-card>
</template>

<style scoped>
</style>
