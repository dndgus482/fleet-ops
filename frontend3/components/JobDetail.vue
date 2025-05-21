<script setup lang="ts">
  import { useRoute } from 'vue-router'
  import { ref, useTemplateRef } from 'vue'
  import { defaultJob, type Job } from '@/types/job'
  import router from '@/router'
  import ConfirmDialog from '@/components/ui/ConfirmDialog.vue'
  import DropDown from '@/components/ui/DropDown.vue'
  import { jobApi } from '@/api/api.ts'
  import { useDataMode } from '@/composables/useDataMode'
  import { useFormMode } from '@/composables/useFormMode'


  const route = useRoute()
  const jobId = String(route.params.jobId)

  const dataMode = useDataMode(() => jobId === 'new')
  const isUpdate = ref(false)
  const { isCreateMode, isUpdateMode, isReadMode } = useFormMode({
    dataMode,
    updateFlag: isUpdate,
  })
  const jobTypes = ref([
    { label: 'SSH', value: 'SSH' },
  ])


  const saveDialog = useTemplateRef('saveDialog')
  const deleteDialog = useTemplateRef('deleteDialog')

  const currentJob = ref<Job>(defaultJob())
  const fetchedJob = ref<Job | null>(null)

  function pressUpdate() {
    isUpdate.value = true
  }

  function pressCancel() {
    if (isCreateMode.value) {
      router.back()
    } else {
      isUpdate.value = false
    }
  }

  async function pressSave() {
    if (!saveDialog.value) {
      return
    }
    const result = await saveDialog.value.reveal()
    if (result.isCanceled) {
      return
    }
    await saveJob()
  }

  async function pressDelete() {
    if (!deleteDialog.value) {
      return
    }
    const result = await deleteDialog.value.reveal()
    if (result.isCanceled) {
      return
    }
    await deleteJob()
  }

  async function pressJobExecutions() {
    await router.push({
      name: 'jobExecutionList',
      query: {
        jobId: jobId,
      },
    })
  }

  async function pressJobHistory() {
    await router.push({ name: 'jobHistoryList', params: { jobId } })
  }


  async function fetchJob() {
    const job = (await jobApi.getJobById(jobId)).data
    fetchedJob.value = job
    currentJob.value = job
  }

  async function saveJob() {
    if (isUpdateMode.value) {
      await jobApi.updateJob(jobId, currentJob.value)
    } else {
      await jobApi.createJob(currentJob.value)
    }
    await router.push({ name: 'jobList' })
  }

  async function deleteJob() {
    await jobApi.deleteJobById(currentJob.value.jobId)
    await router.push({ name: 'jobList' })
  }

  const newAgentUser = ref('')
  const newAgentIp = ref('')

  function addAgent() {
    if (newAgentUser.value.trim() && newAgentIp.value.trim()) {
      if (!currentJob.value.targetAgents) {
        currentJob.value.targetAgents = []
      }
      currentJob.value.targetAgents?.push({
        targetAgentType: 'AGENT',
        ip: newAgentIp.value.trim(),
        userName: newAgentUser.value.trim(),
      })
      newAgentUser.value = ''
      newAgentIp.value = ''
    }
  }

  function removeAgent(index: number) {
    currentJob.value.targetAgents?.splice(index, 1)
  }


  if (isReadMode.value) {
    fetchJob()
  }

</script>

<template>
  <div class="p-6 max-w-xl mx-auto bg-white shadow-lg rounded-xl">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold">Job Editor</h2>
      <div class="space-x-2">
        <button v-if="isReadMode" @click="pressUpdate" class="btn-primary">Edit</button>
        <button v-if="isReadMode" @click="pressDelete" class="btn-danger">Delete</button>
        <button v-if="isReadMode" @click="pressJobExecutions" class="btn-secondary">Executions
        </button>
        <button v-if="isReadMode" @click="pressJobHistory" class="btn-secondary">Job History
        </button>
      </div>
    </div>

    <div class="space-y-3">
      <label class="block">Job Name</label>
      <input :disabled="isReadMode" v-model="currentJob.jobName" class="input-field" />

      <label class="block">Job Description</label>
      <textarea :disabled="isReadMode" v-model="currentJob.jobDescription"
                class="input-field h-24"></textarea>

      <label class="block">Job Type</label>
      <DropDown :disabled="isReadMode" v-model="currentJob.jobType" :options="jobTypes" />

      <label class="block">Target Agents</label>
      <div class="flex gap-2 mb-3">
        <input v-model="newAgentUser" class="border px-2 py-1 rounded w-1/2" placeholder="User" />
        <input v-model="newAgentIp" class="border px-2 py-1 rounded w-1/2" placeholder="IP" />
        <button @click="addAgent" class="bg-gray-600 text-white px-3 py-1 rounded">Add</button>
      </div>
      <div class="flex flex-wrap gap-2">
        <span
          v-for="(agent, idx) in currentJob.targetAgents"
          :key="agent.userName || '' + agent.ip || '' + idx"
          class="bg-gray-100 text-gray-800 px-2 py-1 rounded text-sm flex items-center gap-1"
        >
          {{ agent.userName }}@{{ agent.ip }}
          <button @click.stop="removeAgent(idx)" class="text-xs text-red-500 hover:text-red-700">×</button>
        </span>
      </div>

      <label class="block">Period</label>
      <input :disabled="isReadMode" v-model="currentJob.period" class="input-field" />

      <label class="block">Script</label>
      <textarea :disabled="isReadMode" v-model="currentJob.script"
                class="input-field h-24"></textarea>
    </div>

    <div class="flex justify-end mt-4 space-x-2">
      <button v-if="!isReadMode" @click="pressCancel" class="btn-secondary">Cancel</button>
      <button v-if="!isReadMode" @click="pressSave" class="btn-primary">Save</button>
    </div>

    <ConfirmDialog ref="saveDialog" message="Save?" />
    <ConfirmDialog ref="deleteDialog" message="Are you sure to Delete?" />
  </div>
</template>

<style scoped>
  .btn-primary {
    @apply bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700;
  }

  .btn-secondary {
    @apply bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500;
  }

  .btn-danger {
    @apply bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700;
  }

  .input-field {
    @apply w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500;
  }
</style>
