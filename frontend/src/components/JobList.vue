<script setup lang="ts">
import { h } from 'vue'
import router from '@/router'
import {
  defaultSearchJobReq,
  type Job,
  type JobExecuteRes,
} from '@/types/job.ts'
import { useAsyncState } from '@vueuse/core'
import { jobApi, jobExecutionApi } from '@/api/api.ts'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { NSwitch, useDialog } from 'naive-ui'
import { useAsyncFn } from '@/composables/useAsyncFn.ts'
import { dialogOptions } from '@/components/ui/dialogOptions.ts'
import ClientSideDataTable from '@/components/ui/ClientSideDataTable.vue'

const dialog = useDialog()

const searchJobReq = defaultSearchJobReq()

const fetchJobs = useAsyncState<Job[]>(async () => {
  const res = await jobApi.searchJob({
    ...searchJobReq.filter,
    ...searchJobReq.sort,
    ...searchJobReq.page,
  })
  return res.data.results
}, [])

const switchActive = useAsyncFn(async (job: Job) => {
  await jobApi.switchActiveJob(job.jobId, { active: !job.active })
  job.active = !job.active
})

const executeJob = async (jobId: string) => {
  const jobExecuteRes = (await jobExecutionApi.executeJob(jobId)).data
  await processExecuted(jobExecuteRes)
}

const processExecuted = async ({ jobId, jobExecutionNo }: JobExecuteRes) => {
  dialog.create({
    ...dialogOptions,
    title: 'Succeeded',
    content: 'Executed! Do you want to go see the execution status?',
    negativeText: 'No',
    positiveText: 'Yes',
    onPositiveClick: () => goToExecution(jobId, jobExecutionNo),
  })
}

const pressExecuteJob = async (jobId: string) => {
  dialog.create({
    ...dialogOptions,
    type: 'error',
    title: 'Confirm Execute',
    content: 'Execute the job?',
    negativeText: 'Cancel',
    positiveText: 'Execute',
    onPositiveClick: () => executeJob(jobId),
  })
}

const pressCreate = async () => {
  await router.push({ name: 'jobDetail', params: { jobId: 'new' } })
}

const goToDetail = async (jobId: string) => {
  await router.push({ name: 'jobDetail', params: { jobId } })
}

const goToExecution = async (jobId: string, jobExecutionNo: number) => {
  await router.push({
    name: 'jobExecutionDetail',
    params: { jobId, jobExecutionNo: String(jobExecutionNo) },
  })
}

// UI

const jobListColumn = {
  active: () => ({
    title: 'Active',
    key: 'active',
    width: '10%',
    render(row: Job) {
      return h(NSwitch, {
        loading: switchActive.isLoading.value,
        value: row.active,
        'onUpdate:value': (_: boolean) => {
          switchActive.execute(row)
        },
        onClick: (e: MouseEvent) => {
          e.stopPropagation()
        },
      })
    },
  }),
  jobName: () => ({
    title: 'Job Name',
    key: 'jobName',
    ellipsis: {
      tooltip: true,
    },
    sorter: 'default',
  }),
  period: () => ({
    title: 'Interval(min)',
    key: 'period',
  }),
  action: () => ({
    title: 'Action',
    key: 'action',
    render(row: Job) {
      return h(
        BaseIconButton,
        {
          icon: 'lucide:play',
          round: true,
          size: 'small',
          onClick: (e: MouseEvent) => {
            e.stopPropagation()
            pressExecuteJob(row.jobId)
          },
        },
        { default: () => 'Execute' },
      )
    },
  }),
}

const rowProps = (row: Job) => {
  return {
    class: 'cursor-pointer',
    onClick: () => {
      goToDetail(row.jobId)
    },
  }
}
</script>

<template>
  <n-card title="Job List">
    <template #header-extra>
      <base-icon-button
        @click="pressCreate"
        icon="lucide:plus"
        type="primary"
        secondary
      >
        New
      </base-icon-button>
    </template>
    <client-side-data-table
      :searchable-keys="['jobName', 'active']"
      :columns="[
        jobListColumn.active(),
        jobListColumn.jobName(),
        jobListColumn.period(),
        jobListColumn.action(),
      ]"
      :row-props="rowProps"
      :data="fetchJobs.state.value"
    />
  </n-card>
</template>
