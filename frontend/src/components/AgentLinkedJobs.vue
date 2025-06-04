<script setup lang="ts">
import { agentGroupApi } from '@/api/api.ts'
import type { SimpleJobNameRes } from '@/types/job.ts'
import { useRouter } from 'vue-router'
import { useAsyncState } from '@vueuse/core'

const { ip, userName } = defineProps<{
  ip: string
  userName: string
}>()

const router = useRouter()

const fetchJob = useAsyncState<SimpleJobNameRes[]>(
  async () => (await agentGroupApi.getAgentLinkedJobs({ ip, userName })).data,
  [],
)

const goToJob = async (jobId: string) => {
  await router.push({ name: 'jobDetail', params: { jobId } })
}
</script>

<template>
  <n-card title="Linked Job List">
    <n-list hoverable clickable data-testid="job-list">
      <template v-if="fetchJob.state.value.length === 0">
        No Linked Jobs
      </template>
      <n-list-item
        v-for="job in fetchJob.state.value"
        :key="job.jobId"
        :data-testid="`job-${job.jobId}`"
        @click="goToJob(job.jobId)"
      >
        {{ job.jobName }}
      </n-list-item>
    </n-list>
  </n-card>
</template>
