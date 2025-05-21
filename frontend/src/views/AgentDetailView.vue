<script setup lang="ts" xmlns="http://www.w3.org/1999/html">
  import AgentDetail from '@/components/AgentDetail.vue'
  import AgentLinkedJobs from '@/components/AgentLinkedJobs.vue'
</script>

<template>
  <main>
    <n-card class="max-w-3xl mx-auto p-4 space-y-6" embedded>
      <n-flex vertical :size="16">
        <AgentDetail />
        <n-divider title-placement="left">
        </n-divider>
        <AgentLinkedJobs />
      </n-flex>
    </n-card>
  </main>
</template>
