<script setup lang="ts">
import { onMounted, ref } from 'vue'
import router from '@/router'
import type { JobHistory } from '@/types/job.ts'
import { useRoute } from 'vue-router'
import { jobHistoryApi } from '@/api/api.ts'

const route = useRoute()
const jobId = String(route.params.jobId)

const fetchedJobHistory = ref<JobHistory[]>([])


async function goToDetail (jobHistoryId: string) {
  await router.push({ name: 'jobHistoryDetail', params: { jobId, jobHistoryId } })
}

async function fetchJobHistory() {
  fetchedJobHistory.value = (await jobHistoryApi.getJobHistoryByJobId(jobId)).data
}

onMounted(fetchJobHistory)
</script>

<template>
  <ul>
    <li v-for="jobHistory in fetchedJobHistory" :key="jobHistory.jobHistoryNo" @click="goToDetail(jobHistory.jobHistoryNo)">
      <h3>{{ jobHistory.jobHistoryNo }}</h3>
      <p>{{ jobHistory.jobId }}</p>
      <p>{{ jobHistory.regDateTime }}</p>
    </li>
  </ul>
</template>

<style scoped>

</style>
