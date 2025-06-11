<script setup lang="ts">
import router from '@/router'
import type { SimpleJobNameRes } from '@/types/job.ts'
import { agentGroupApi } from '@/api/api.ts'
import { useAsyncState } from '@vueuse/core'

const { agentGroupId } = defineProps<{
  agentGroupId: string
}>()

const fetchJob = useAsyncState<SimpleJobNameRes[]>(
  async () => (await agentGroupApi.getAgentGroupLinkedJobs(agentGroupId)).data,
  [],
)

const goToJob = async (jobId: string) => {
  await router.push({ name: 'jobDetail', params: { jobId: jobId } })
}
</script>

<template>
  <n-card title="Linked Jobs">
    <n-list hoverable clickable>
      <template v-if="fetchJob.state.value.length === 0">
        No Linked Jobs
      </template>
      <n-list-item
        v-for="job in fetchJob.state.value"
        :key="job.jobId"
        @click="goToJob(job.jobId)"
      >
        {{ job.jobName }}
      </n-list-item>
    </n-list>
  </n-card>
</template>

<style scoped></style>
