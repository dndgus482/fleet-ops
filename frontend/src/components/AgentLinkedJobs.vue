<script setup lang="ts">
import { ref } from 'vue'
import { agentGroupApi } from '@/api/api.ts'
import type { SimpleJobNameRes } from '@/types/job.ts'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const ip = String(route.params.ip)
const userName = String(route.params.userName)

const jobs = ref<SimpleJobNameRes[]>([])

async function goToJob(jobId: string) {
  await router.push({ name: 'jobDetail', params: { jobId } })
}

async function fetchLinkedJobs() {
  const res = await agentGroupApi.getAgentLinkedJobs({ ip, userName })
  jobs.value = res.data
}

fetchLinkedJobs()
</script>

<template>
  <n-card title="Linked Job List" class="mx-auto max-w-4xl">
    <n-list hoverable clickable data-testid="job-list">
      <n-list-item
        v-for="job in jobs"
        :key="job.jobId"
        :data-testid="`job-${job.jobId}`"
        @click="goToJob(job.jobId)"
      >
        {{ job.jobName }}
      </n-list-item>
    </n-list>
  </n-card>
</template>
