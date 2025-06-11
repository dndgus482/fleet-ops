<script setup lang="ts">
import { ref, toRef, watchEffect } from 'vue'
import { defaultJob, type Job } from '@/types/job'
import { jobApi } from '@/api/api.ts'
import { DataMode } from '@/composables/useDataMode'
import { useFormMode } from '@/composables/useFormMode'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { useAsyncState } from '@vueuse/core'
import { useDialog } from 'naive-ui'
import { dialogOptions } from '@/components/ui/dialogOptions.ts'
import { useRouter } from 'vue-router'
import { jobColumn } from '@/components/JobDetailSchema.ts'
import JobDetailEdit from '@/components/JobDetailEdit.vue'

const props = defineProps<{
  jobId: string
  dataMode: DataMode
}>()
const jobId = toRef(props, 'jobId')
const dataMode = toRef(props, 'dataMode')

const router = useRouter()
const dialog = useDialog()

const updateFlag = ref(false)
const { formMode, isCreateMode, isReadMode } = useFormMode(dataMode, updateFlag)

const fetchJob = useAsyncState<Job>(
  async () => (await jobApi.getJobById(jobId.value)).data,
  defaultJob(),
  { immediate: true },
)

watchEffect(() => {
  if (!isCreateMode.value && jobId.value) {
    fetchJob.execute()
  }
})

function pressUpdate() {
  updateFlag.value = true
}

function cancel() {
  if (isCreateMode.value) {
    router.back()
  } else {
    updateFlag.value = false
  }
}

async function save(jobId: string) {
  if (isCreateMode.value) {
    await router.replace({ name: 'jobDetail', params: { jobId } })
  } else {
    updateFlag.value = false
    await fetchJob.execute()
  }
}

async function deleteJob() {
  await jobApi.deleteJobById(jobId.value)
  await router.replace({ name: 'jobList' })
}

async function pressDelete() {
  dialog.create({
    ...dialogOptions,
    type: 'error',
    title: 'Confirm Delete',
    content: 'Are you sure you want to delete?',
    negativeText: 'Cancel',
    positiveText: 'Delete',
    onPositiveClick: deleteJob,
  })
}

async function pressJobExecutions() {
  await router.push({
    name: 'jobExecutionList',
    query: {
      jobId: jobId.value,
    },
  })
}

async function pressJobHistories() {
  await router.push({ name: 'jobHistoryList', params: { jobId: jobId.value } })
}
</script>

<template>
  <n-card
    v-if="isReadMode"
    :title="fetchJob.state.value.jobName ?? ''"
  >
    <template #header-extra>
      <div class="flex gap-2">
        <base-icon-button
          type="default"
          secondary
          @click="pressJobHistories"
          data-testid="jobHistoriesButton"
          icon="lucide:history"
        >
          Change History
        </base-icon-button>
        <base-icon-button
          type="default"
          secondary
          @click="pressJobExecutions"
          data-testid="jobExecutionsButton"
          icon="lucide:list"
        >
          Execution Records
        </base-icon-button>
        <base-icon-button
          type="default"
          secondary
          @click="pressUpdate"
          data-testid="updateButton"
          icon="lucide:pencil"
        >
          Edit
        </base-icon-button>
        <base-icon-button
          type="error"
          secondary
          @click="pressDelete"
          data-testid="deleteButton"
          icon="lucide:trash-2"
        >
          Delete
        </base-icon-button>
      </div>
    </template>
    <span class="opacity-80" v-if="!fetchJob.state.value.active">
            ⚠️ This Job is disabled
    </span>
    <n-divider/>
    <!-- Job Description -->
    <n-thing v-if="fetchJob.isReady.value">
      <template #header>Group Description</template>
      <div class="whitespace-pre-wrap">
        {{ fetchJob.state.value.jobDescription }}
      </div>
    </n-thing>
    <n-skeleton v-else text />
    <n-divider />

    <!-- Job Type -->
    <n-thing v-if="fetchJob.isReady.value">
      <template #header>Group Description</template>
      {{ fetchJob.state.value.jobType }}
    </n-thing>
    <n-skeleton v-else />
    <n-divider />

    <!-- Agents -->
    <n-thing v-if="fetchJob.isReady.value">
      <template #header>Target Agents</template>
      <n-data-table
        :columns="[
            jobColumn.agentGroup(),
            jobColumn.ip(),
            jobColumn.userName(),
          ]"
        :data="fetchJob.state.value.targetAgents"
      />
    </n-thing>
    <n-skeleton v-else />
    <n-divider />

    <!-- Period -->
    <n-thing v-if="fetchJob.isReady.value">
      <template #header>Scheduled Period</template>
      {{ fetchJob.state.value.period }}
    </n-thing>
    <n-skeleton v-else />
    <n-divider />

    <n-thing v-if="fetchJob.isReady.value">
      <template #header>Script</template>
      <n-card>
        <n-code  :code="fetchJob.state.value.script" language="bash" />

      </n-card>
    </n-thing>
    <n-skeleton v-else />

  </n-card>
  <job-detail-edit
    v-else
    :job="fetchJob.state.value"
    :form-mode="formMode"
    @cancel="cancel"
    @save="save"
  />
</template>
