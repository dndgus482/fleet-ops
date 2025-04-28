<script setup lang="ts">
  import { ref, useTemplateRef } from 'vue'
  import router from '@/router'
  import { defaultSearchJobExecutionReq, type JobExecution } from '@/types/job.ts'
  import { toRelativeDuration, toRelativeTime } from '@/util/dateTimeFormat.ts'
  import JobExecutionSearchBar from '@/components/JobExecutionSearchBar.vue'
  import { useSearchFilter } from '@/composables/useSearchFilter.ts'
  import { useSearchSort } from '@/composables/useSearchSort.ts'
  import { useInfiniteScroll } from '@vueuse/core'
  import SortableTableHeader from '@/components/ui/SortableTableHeader.vue'
  import { jobExecutionApi } from '@/api/api.ts'

  const fetchedJobExecutions = ref<JobExecution[]>([])

  const searchJobExecutionReq = defaultSearchJobExecutionReq()

  const el = useTemplateRef<HTMLElement>('jobListRef')

  const fields = [
    { key: 'jobName', label: 'JobName' },
    { key: 'jobExecutionNo', label: '#' },
    { key: 'jobExecutionStatus', label: 'Status' },
    { key: 'startDateTime', label: 'Started' },
    { key: 'duration', label: 'Duration' },
  ]

  useInfiniteScroll(
    el,
    fetchJobExecutions,
    {
      distance: 10,
      canLoadMore: () => {
        return searchJobExecutionReq.page.pageToken != null
      },
    },
  )
  const { searchFilter } = useSearchFilter(searchJobExecutionReq.filter, newFetchJobExecutions)
  const { sort } = useSearchSort(searchJobExecutionReq.sort, newFetchJobExecutions)

  fetchJobExecutions()

  async function fetchJobExecutions() {
    const jobExecutionPagedResult = (await jobExecutionApi.searchJobExecution({
      ...searchFilter.value,
      ...sort.value,
      ...searchJobExecutionReq.page,
    })).data
    searchJobExecutionReq.page.pageToken = jobExecutionPagedResult.nextPageToken
    fetchedJobExecutions.value.push(...jobExecutionPagedResult.results)
  }

  async function newFetchJobExecutions() {
    searchJobExecutionReq.page.pageToken = undefined
    fetchedJobExecutions.value = []
    await fetchJobExecutions()
  }


  async function goToDetail(jobId: string, jobExecutionNo: number) {
    await router.push({
      name: 'jobExecutionDetail',
      params: {
        jobId,
        jobExecutionNo: jobExecutionNo.toString(),
      },
    })
  }
</script>
<template>
  <div class="p-6 max-w-5xl mx-auto bg-white shadow-md rounded-lg">
    <h1 class="text-2xl font-bold mb-4">Job Executions</h1>

    <JobExecutionSearchBar v-model:searchKeyword="searchFilter" />

    <SortableTableHeader v-model="sort" :fields="fields">
      <template #column="{ field, isActive, direction, toggle }">
        <div class="flex items-center gap-1 cursor-pointer" @click="toggle">
          <span>{{ field.label }}</span>
          <span>{{ isActive ? (direction === 'ASC' ? '▲' : '▼') : '' }}</span>
        </div>
      </template>
    </SortableTableHeader>

    <div class="overflow-auto max-h-[600px]">
      <ul ref="jobListRef" class="divide-y divide-gray-100">
        <li
          v-for="jobExecution in fetchedJobExecutions"
          :key="jobExecution.jobExecutionNo"
          @click="goToDetail(jobExecution.jobId, jobExecution.jobExecutionNo)"
          class="grid grid-cols-5 py-3 items-center hover:bg-gray-50 transition cursor-pointer">
          <div class="px-2 text-gray-700">{{ jobExecution.jobName }}</div>
          <div class="px-2 text-gray-700">{{ jobExecution.jobExecutionNo }}</div>
          <div class="px-2">
            <span
              class="px-2 py-0.5 rounded text-xs font-medium"
              :class="{
                'bg-green-100 text-green-700': jobExecution.jobExecutionStatus === 'COMPLETED',
                'bg-yellow-100 text-yellow-700': jobExecution.jobExecutionStatus === 'STARTED',
                'bg-red-100 text-red-700': jobExecution.jobExecutionStatus === 'FAILED',
                'bg-gray-200 text-gray-600': !['COMPLETED', 'STARTED', 'FAILED'].includes(jobExecution.jobExecutionStatus)
              }">
              {{ jobExecution.jobExecutionStatus }}
            </span>
          </div>
          <div class="px-2 text-gray-600">{{ toRelativeTime(jobExecution.startDateTime) }}</div>
          <div class="px-2 text-gray-600">
            {{ toRelativeDuration(jobExecution.startDateTime, jobExecution.endDateTime) }}
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
