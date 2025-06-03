<script setup lang="ts">
import { onMounted, ref } from 'vue'
import router from '@/router'
import type { SimpleJobNameRes } from '@/types/job.ts'
import { useRoute } from 'vue-router'
import { agentGroupApi } from '@/api/api.ts'

const route = useRoute()
const agentGroupId = String(route.params.agentGroupId)

const jobs = ref<SimpleJobNameRes[]>([])

async function goToJob(jobId: string) {
  await router.push({ name: 'jobDetail', params: { jobId: jobId } })
}

async function fetchLinkedJobs() {
  jobs.value = (await agentGroupApi.getAgentGroupLinkedJobs(agentGroupId)).data
}

onMounted(fetchLinkedJobs)
</script>

<template>
  <n-card title="Linked Jobs">
    <n-list hoverable clickable>
      <n-list-item
        v-for="job in jobs"
        :key="job.jobId"
        @click="goToJob(job.jobId)"
        >{{ job.jobName }}</n-list-item
      >
    </n-list>
  </n-card>
</template>

<style scoped></style>
