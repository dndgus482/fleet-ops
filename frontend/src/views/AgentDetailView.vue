<script setup lang="ts" xmlns="http://www.w3.org/1999/html">
import AgentDetail from '@/components/AgentDetail.vue'
import AgentLinkedJobs from '@/components/AgentLinkedJobs.vue'

const { ip, userName } = defineProps<{
  ip: string
  userName: string
}>()
</script>

<template>
  <main>
    <div class="flex flex-col items-center">
      <agent-detail :ip="ip" :user-name="userName" />
      <n-divider />
      <agent-linked-jobs :ip="ip" :user-name="userName" />
    </div>
  </main>
</template>
