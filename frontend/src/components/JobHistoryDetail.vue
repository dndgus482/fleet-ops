<script setup lang="ts">
  import { ref } from 'vue'
  import { defaultJobHistory, type JobHistory } from '@/types/job.ts'
  import { useRoute } from 'vue-router'
  import { jobHistoryApi } from '@/api/api.ts'

  const route = useRoute()
  const jobId = String(route.params.jobId)
  const jobHistoryId = Number(route.params.jobHistoryId)

  const fetchedJobHistory = ref<JobHistory>(defaultJobHistory())

  async function fetchJobExecutions() {
    fetchedJobHistory.value = (await jobHistoryApi.getJobHistoryById(jobId, jobHistoryId)).data
  }

  fetchJobExecutions()
</script>

<template>
  <div>
    <p>Job Id</p>
    <p>{{ fetchedJobHistory.jobId }}</p>
    <p>Job History Id</p>
    <p>{{ fetchedJobHistory.jobHistoryNo }}</p>
    <p>End DateTime</p>
    <p>{{ fetchedJobHistory.regDateTime }}</p>
  </div>
</template>

<style scoped>

</style>
