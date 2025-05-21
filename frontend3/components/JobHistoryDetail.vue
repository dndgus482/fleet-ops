<script setup lang="ts">
  import { ref } from 'vue'
  import { defaultJobHistory, type JobHistory } from '@/types/job'
  import { useRoute, useRouter } from 'vue-router'
  import { jobHistoryApi } from '@/api/api.ts'
  import InfoField from '@/components/ui/InfoField.vue'
  import ResponsiveGrid from '@/components/ui/ResponsiveGrid.vue'
  import { formatDateTime } from '@/util/dateTimeFormat.ts'

  const route = useRoute()
  const router = useRouter()
  const jobId = String(route.params.jobId)
  const jobHistoryNo = Number(route.params.jobHistoryNo)

  const fetchedJobHistory = ref<JobHistory>(defaultJobHistory())

  async function fetchJobHistory() {
    try {
      fetchedJobHistory.value = (await jobHistoryApi.getJobHistoryById(jobId, jobHistoryNo)).data
    } catch (error) {
      console.error('Error while fetching job history:', error)
    }
  }

  async function backToJobHistoryList() {
    await router.push({ name: 'jobHistoryList', params: { jobId } })
  }

  fetchJobHistory()
</script>

<template>
  <div class="p-6 max-w-3xl mx-auto bg-white shadow-lg rounded-xl">
    <div class="flex justify-between items-center mb-6">
      <h2 class="text-2xl font-bold">Job History Details</h2>
      <button @click="backToJobHistoryList" class="btn-secondary">Back to List</button>
    </div>

    <ResponsiveGrid>
      <InfoField label="Job Name" :value="fetchedJobHistory.jobName" />
      <InfoField label="Job History No" :value="fetchedJobHistory.jobHistoryNo" />
      <InfoField label="Registered User" :value="fetchedJobHistory.regUserId" />
      <InfoField label="Registered Time" :value="formatDateTime(fetchedJobHistory.regDateTime)" />
      <InfoField label="Job Type" :value="fetchedJobHistory.jobType" />
    </ResponsiveGrid>

    <div class="mt-8 space-y-4">
      <template v-for="(value, key) in fetchedJobHistory.history" :key="key">
        <div v-if="!['jobId', 'jobName', 'jobHistoryNo', 'regUserId', 'regDateTime', 'jobType', 'active'].includes(key)">
          <label class="block font-semibold mb-1 text-gray-800">{{ key }}</label>

          <!-- If SSH script -->
          <pre
            v-if="key === 'script'"
            class="bg-gray-50 p-2 rounded text-sm text-gray-700 whitespace-pre-wrap break-all"
          >{{ value }}</pre>

          <!-- If array type: JSON.stringify -->
          <pre
            v-else-if="Array.isArray(value)"
            class="bg-gray-50 p-2 rounded text-sm text-gray-700 whitespace-pre-wrap break-all"
          >{{ JSON.stringify(value, null, 2) }}</pre>

          <!-- If object type: JSON.stringify -->
          <pre
            v-else-if="typeof value === 'object' && value !== null"
            class="bg-gray-50 p-2 rounded text-sm text-gray-700 whitespace-pre-wrap break-all"
          >{{ JSON.stringify(value, null, 2) }}</pre>

          <!-- If primitive type: value -->
          <p v-else class="text-gray-700">{{ value }}</p>
        </div>
      </template>
    </div>
  </div>
</template>

<style scoped>
  .btn-secondary {
    @apply px-4 py-2 bg-blue-500 text-white font-semibold rounded-lg hover:bg-blue-600;
  }
</style>
