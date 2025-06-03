<script setup lang="ts">
import type { Agent } from '@/types/agentGroup.ts'
import { ref, watchEffect } from 'vue'
import { useAgentConnection } from '@/components/AgentDetailSchema.js'
import { CaretForwardOutline } from '@vicons/ionicons5'

const { ip, userName } = defineProps<{
  ip: string
  userName: string
}>()

const fetchedAgent = ref<Agent>({
  ip,
  userName,
})
const connection = useAgentConnection(fetchedAgent.value)

const toggleErrorLog = ref<'errorLog' | ''>('errorLog')

watchEffect(() => {
  if (!connection.isLoading.value && connection.isLogVisible.value) {
    toggleErrorLog.value = 'errorLog'
  }
})

const handleItemHeaderClick = () => {
  toggleErrorLog.value = toggleErrorLog.value === 'errorLog' ? '' : 'errorLog'
}
</script>

<template>
  <n-card title="Agent">
    <div class="flex flex-col gap-2">
      <n-descriptions
        label-placement="left"
        :column="1"
        bordered
        label-class="whitespace-nowrap w-0"
      >
        <n-descriptions-item label="IP">
          <span data-testid="agent-ip">{{ fetchedAgent.ip }}</span>
        </n-descriptions-item>
        <n-descriptions-item label="User Name">
          <span data-testid="agent-userName">{{ fetchedAgent.userName }}</span>
        </n-descriptions-item>
      </n-descriptions>

      <div class="flex items-center gap-2">
        <n-button
          data-testid="test-connection-btn"
          @click="connection.execute"
          strong
          secondary
          round
          type="primary"
          :loading="connection.isLoading.value"
        >
          <template #icon>
            <n-icon>
              <CaretForwardOutline />
            </n-icon>
          </template>
          Test Connection
        </n-button>

        <span
          v-if="connection.connected.value !== undefined"
          :class="[
            connection.connected.value ? 'text-green-500' : 'text-red-500',
          ]"
          data-testid="connection-status"
        >
          {{ connection.connected.value ? 'Success' : 'Failed' }}
        </span>
      </div>

      <n-card embedded v-if="connection.isLogVisible.value">
        <n-collapse
          :expanded-names="toggleErrorLog"
          @item-header-click="handleItemHeaderClick"
        >
          <n-collapse-item name="errorLog" data-testid="log-toggle">
            <template #header>
              <span class="text-red-500">Error Log</span>
            </template>
            <n-scrollbar v-if="connection.isLogVisible.value">
              <n-code
                data-testid="error-log"
                :code="connection.log.value"
                word-wrap
              />
            </n-scrollbar>
          </n-collapse-item>
        </n-collapse>
      </n-card>
    </div>
  </n-card>
</template>
