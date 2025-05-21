<script setup lang="ts">
  import { onMounted, ref } from 'vue'
  import { defaultSearchJobReq, type Job } from '~/types/job'
  import type { JobExecuteRes } from '~/generated'
  import { jobApi, jobExecutionApi } from '~/api/api'
  import ConfirmDialog from '~/components/ui/ConfirmDialog.vue'

  const fetchedJobs = ref<Job[]>([])

  const overlay = useOverlay()
  const modal = overlay.create(ConfirmDialog)


  async function pressExecuteJob(jobId: string) {
    let result = modal.open({ message: 'pressed' })
    const jobExecuteRes = await executeJob(jobId)
    await processExecuted(jobExecuteRes)
  }

  async function processExecuted({ jobId, jobExecutionNo }: JobExecuteRes) {
    let result = modal.open({ message: 'pressed' })
    await navigateTo({ path: '/jobExecutionDetail', query: { jobId, jobExecutionNo: String(jobExecutionNo) } })
  }

  async function pressCreate() {
    await navigateTo('/jobDetail/new')
  }

  async function goToDetail(jobId: string) {
    await navigateTo(`/jobDetail/${jobId}`)
  }


  const searchJobReq = defaultSearchJobReq()
  // TODO: 백엔드에서 조회하는게 아닌 직접 필터링
  const { searchFilter } = useSearchFilter(searchJobReq.filter, fetchJobs)
  const { sort } = useSearchSort(searchJobReq.sort, fetchJobs)


  async function fetchJobs() {
    const jobPagedResult = (await jobApi.searchJob({
      ...searchFilter.value,
      ...sort.value,
      ...searchJobReq.page,
    })).data
    fetchedJobs.value = jobPagedResult.results
  }

  async function executeJob(jobId: string) {
    return (await jobExecutionApi.executeJob(jobId)).data
  }

  onMounted(fetchJobs)
</script>
<template>
  <div class="p-6 max-w-4xl mx-auto bg-white shadow-lg rounded-lg relative">
    <h1 class="text-2xl font-bold mb-4">Job List</h1>
    <button @click="pressCreate"
            class="absolute top-6 right-6 p-2 rounded-full shadow-lg hover:bg-gray-200 transition-transform transform hover:scale-110 flex items-center justify-center border border-gray-300">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2"
           stroke="currentColor" class="w-6 h-6 text-gray-600">
        <path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4" />
      </svg>
    </button>
    <JobSearchBar v-model:searchKeyword="searchFilter" />
    <div class="w-full mt-4">
      <div class="grid grid-cols-3 text-gray-500 text-sm font-semibold pb-2 border-b">
        <div class="px-2">Job Name</div>
        <div class="px-2">Period</div>
        <div class="px-2 text-right">Action</div>
      </div>
      <ul class="divide-y divide-gray-100">
        <li v-for="job in fetchedJobs" :key="job.jobId" @click="goToDetail(job.jobId)"
            class="grid grid-cols-3 py-3 items-center cursor-pointer hover:bg-gray-50 transition">
          <div class="px-2 font-medium text-gray-800">{{ job.jobName }}</div>
          <div class="px-2 text-gray-600">{{ job.period }}</div>
          <div class="px-2 text-right">
            <button @click.stop="pressExecuteJob(job.jobId)"
                    class="px-3 py-1 text-sm text-white bg-blue-500 rounded-lg hover:bg-blue-600">
              Execute
            </button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
