<script setup lang="ts">
import JobDetail from '@/components/JobDetail.vue'

const { jobId } = defineProps<{ jobId: string }>()
</script>

<template>
  <main>
    <JobDetail :jobId="jobId" />
  </main>
</template>
