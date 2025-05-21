<script setup lang="ts">
  import type { SimpleJobNameRes } from '@/types/job.ts'
  import { agentGroupApi } from '~/api/api'

  const route = useRoute()
  const agentGroupId = String(route.params.agentGroupId)

  const jobs = ref<SimpleJobNameRes[]>([])

  async function goToJob(jobId: string) {
    await navigateTo(`/jobDetail/${jobId}`)
  }

  async function fetchLinkedJobs() {
    jobs.value = (await agentGroupApi.getAgentGroupLinkedJobs(agentGroupId)).data
  }

  onMounted(fetchLinkedJobs)
</script>

<template>
  <UCard title="Linked Jobs">
    <template #header>
      Linked Jobs
    </template>
    <ul>
      <li class="btn-hoverable"
          v-for="job in jobs" :key="job.jobId" @click="goToJob(job.jobId)">
        {{ job.jobName }}
      </li>
    </ul>
  </UCard>
</template>

<style scoped>
</style>
