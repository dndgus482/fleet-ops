<script setup lang="ts">
  import type { Agent } from '@/types/agentGroup.ts'
  import KeyValueGrid from '~/components/ui/KeyValueGrid.vue'
  import { useAgentConnection } from '~/components/AgentDetailSchema.vue'

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
  <UCard title="Agent">
    <div class="flex flex-col gap-2">
      <KeyValueGrid :data="{ 'IP': ip,'User Name': userName }" />
      <div class="flex gap-2">
        <UButton
          class="h-10"
          label="Test Connection"
          data-testid="test-connection-btn"
          @click="connection.execute()"
          variant="subtle"
          :loading="connection.isLoading.value"
        />
        <UCollapsible v-model:open="connection.isLogVisible.value">
          <UBadge
            v-if="connection.state.value.connected != null"
            :label="connection.statusText.value"
            :color="connection.state.value.connected ? 'success' : 'error'"
            :leading-icon="connection.state.value.log ? 'lucide-chevron-right' : ''"
            :ui="{
              leadingIcon: connection.isLogVisible.value ? 'duration-200 rotate-90' : ''
            }"
            variant="soft"
          />
          <template #content>
            {{ connection.state.value.log }}
          </template>
        </UCollapsible>
      </div>
    </div>
  </UCard>
</template>

<style scoped>
</style>
