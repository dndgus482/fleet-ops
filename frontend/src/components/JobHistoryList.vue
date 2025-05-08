<script setup lang="ts">
  import { ref, useTemplateRef } from 'vue'
  import router from '@/router'
  import { defaultSearchJobHistoryReq, type JobHistory } from '@/types/job.ts'
  import { useSearchSort } from '@/composables/useSearchSort.ts'
  import SortableTableHeader from '@/components/ui/SortableTableHeader.vue'
  import { jobHistoryApi } from '@/api/api.ts'
  import { useRoute } from 'vue-router'
  import { useInfiniteScroll } from '@vueuse/core'
  import { formatDateTime } from '@/util/dateTimeFormat.ts'

  const fetchedJobHistories = ref<JobHistory[]>([])
  const el = useTemplateRef<HTMLElement>('historyListRef')

  const route = useRoute()
  const jobId = String(route.params.jobId)

  const searchJobHistoryReq = defaultSearchJobHistoryReq()


  const { sort } = useSearchSort(searchJobHistoryReq.sort, newFetchJobHistories)
  useInfiniteScroll(
    el,
    fetchJobHistories,
    {
      distance: 10,
      canLoadMore: () => {
        return searchJobHistoryReq.page.pageToken != null
      },
    },
  )

  const tableHeaders = [
    { key: 'jobHistoryNo', label: '#', sortable: true },
    { key: 'regUserId', label: 'Registered User', sortable: false },
    { key: 'regDateTime', label: 'Registered Time', sortable: false },
  ]

  newFetchJobHistories()

  async function fetchJobHistories() {
    const res = (await jobHistoryApi.searchJobHistory(jobId, {
      ...searchJobHistoryReq.page,
      ...sort.value,
    })).data
    fetchedJobHistories.value.push(...res.results)
  }

  async function newFetchJobHistories() {
    searchJobHistoryReq.page.pageToken = undefined
    fetchedJobHistories.value = []
    await fetchJobHistories()
  }

  async function goToDetail(jobId: string, jobHistoryNo: number) {
    await router.push({
      name: 'jobHistoryDetail',
      params: { jobId, jobHistoryNo: jobHistoryNo.toString() },
    })
  }
</script>

<template>
  <div class="p-6 max-w-5xl mx-auto bg-white shadow-md rounded-lg">
    <h1 class="text-2xl font-bold mb-4">Job History of {{ jobId }}</h1>

    <SortableTableHeader v-model="sort" :fields="tableHeaders" />

    <div class="overflow-auto max-h-[600px]">
      <ul ref="historyListRef" class="divide-y divide-gray-100">
        <li
          v-for="jobHistory in fetchedJobHistories"
          :key="jobHistory.jobHistoryNo"
          @click="goToDetail(jobHistory.jobId, jobHistory.jobHistoryNo)"
          class="grid grid-cols-3 py-3 items-center hover:bg-gray-50 transition cursor-pointer"
        >
          <div class="px-2 text-gray-700">{{ jobHistory.jobHistoryNo }}</div>
          <div class="px-2 text-gray-700">{{ jobHistory.regUserId }}</div>
          <div class="px-2 text-gray-700">{{ formatDateTime(jobHistory.regDateTime) }}</div>
        </li>
      </ul>
    </div>
  </div>
</template>
