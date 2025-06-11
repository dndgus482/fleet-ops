<script setup lang="ts">
import JobDetail from '@/components/JobDetail.vue'
import { computed } from 'vue'
import { useDataMode } from '@/composables/useDataMode.ts'

const { jobId } = defineProps<{ jobId: string }>()
const newCondition = computed(() => jobId === 'new')
const { mode } = useDataMode(newCondition)
</script>

<template>
  <main>
    <JobDetail :jobId="jobId" :data-mode="mode" />
  </main>
</template>
