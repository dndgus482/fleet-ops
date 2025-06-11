<script setup lang="ts">
import type { Agent } from '@/types/agentGroup.ts'

const { agents } = defineProps<{
  agents: Agent[]
}>()
</script>

<template>
  <div class="flex flex-col gap-2">
    <div
      v-for="agent in agents"
      :key="agent.ip"
      class="flex items-center gap-2"
    >
      <n-badge :dot="true" :type="agent.connected ? 'success' : 'error'" />
      {{ agent.ip }} ({{ agent.userName }})
    </div>
  </div>
</template>
