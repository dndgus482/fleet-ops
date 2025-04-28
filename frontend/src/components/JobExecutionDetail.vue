<script setup lang="ts">
  import { computed, ref } from 'vue'
  import router from '@/router'
  import {
    defaultJobExecution,
    type JobExecution,
    type SshLiveLog,
  } from '@/types/job.ts'
  import {
    subscribeJobExecutionChange,
    subscribeJobExecutionLogs,
  } from '@/api/jobExecutionApi.ts'
  import { useRoute } from 'vue-router'
  import { formatDateTime } from '../util/dateTimeFormat.ts'
  import ConfirmDialog from '@/components/ui/ConfirmDialog.vue'
  import { templateRef } from '@vueuse/core'
  import { jobExecutionApi } from '@/api/api.ts'

  const route = useRoute()
  const jobId = String(route.params.jobId)
  const jobExecutionNo = Number(route.params.jobExecutionNo)

  const fetchedJobExecution = ref<JobExecution>(defaultJobExecution())

  const stopJobExecutionDialog = templateRef('stopJobExecutionDialog')

  const pressStopJobExecution = async () => {
    if (!stopJobExecutionDialog.value) {
      return
    }
    let result = await stopJobExecutionDialog.value.reveal()
    if (result.isCanceled) {
      return
    }
    await stopJobExecution()
    await router.replace({ path: router.currentRoute.value.fullPath })
  }

  const stopJobExecution = async () => {
    await jobExecutionApi.stopJobExecution(jobId, jobExecutionNo)
  }


  type AgentLog = {
    agentIdx: number,
    logs: Map<number, string>,
  }
  const agentLogs = ref<Map<number, AgentLog>>(new Map<number, AgentLog>())

  function getLogByAgentId(agentIdx: number): string {
    const logMap = agentLogs.value.get(agentIdx)?.logs
    if (!logMap) {
      return fetchedJobExecution.value.targetAgents[agentIdx].log || ''
    }
    return [...logMap.entries()]
      .sort((a, b) => a[0] - b[0])
      .map(([_, line]) => line)
      .join('')
  }

  async function fetchJobExecutions() {
    fetchedJobExecution.value = (await jobExecutionApi.getJobExecutionById(jobId, jobExecutionNo)).data
  }

  function setSshLogs(sshLiveLogs: SshLiveLog[]) {
    sshLiveLogs.forEach(({ agentIdx, lineIdx, log }) => {
      let agentLog = agentLogs.value.get(agentIdx)
      if (!agentLog) {
        agentLogs.value.set(agentIdx, {
          agentIdx: agentIdx,
          logs: new Map<number, string>(),
        })
        agentLog = agentLogs.value.get(agentIdx)
      }
      agentLog!.logs.set(lineIdx, log)
    })
  }

  async function fetchJobExecutionLogs() {
    const sshLiveLogs = (await jobExecutionApi.getLiveLog(jobId, jobExecutionNo)).data
    setSshLogs(sshLiveLogs)
  }


  const isRunning = computed(() => fetchedJobExecution.value.jobExecutionStatus === 'STARTED')

  function goToJobHistory(jobHistoryNo: number) {
    router.push({ name: 'jobHistoryDetail', params: { jobHistoryNo } })
  }

  function goToAgentDetail(ip: string, userName: string) {
    router.push({ name: 'agentDetail', params: { ip, userName } })
  }

  fetchJobExecutions()

  subscribeJobExecutionLogs(jobId, jobExecutionNo, (msg) => {
    setSshLogs([msg])
  })

  subscribeJobExecutionChange(jobId, jobExecutionNo, fetchJobExecutions)

  fetchJobExecutionLogs()
</script>

<template>
  <div class="p-6 max-w-5xl mx-auto bg-white shadow-md rounded-lg space-y-6">
    <h1 class="text-2xl font-bold">Job Execution Detail</h1>

    <div class="grid grid-cols-2 sm:grid-cols-3 gap-4 text-sm text-gray-700">
      <div>
        <div class="text-gray-500 text-xs mb-1">Job Name</div>
        <div>{{ fetchedJobExecution.jobName }}</div>
      </div>
      <div>
        <div class="text-gray-500 text-xs mb-1">Status</div>
        <span
          class="px-2 py-0.5 rounded text-xs font-medium"
          :class="{
        'bg-green-100 text-green-700': fetchedJobExecution.jobExecutionStatus === 'COMPLETED',
        'bg-yellow-100 text-yellow-700': fetchedJobExecution.jobExecutionStatus === 'STARTED',
        'bg-red-100 text-red-700': fetchedJobExecution.jobExecutionStatus === 'FAILED',
        'bg-gray-200 text-gray-600': !['COMPLETED', 'STARTED', 'FAILED'].includes(fetchedJobExecution.jobExecutionStatus)
      }"
        >
      {{ fetchedJobExecution.jobExecutionStatus }}
    </span>
      </div>
      <div>
        <div class="text-gray-500 text-xs mb-1">Start Time</div>
        <div>{{ formatDateTime(fetchedJobExecution.startDateTime) }}</div>
      </div>
      <div>
        <div class="text-gray-500 text-xs mb-1">End Time</div>
        <div>{{ formatDateTime(fetchedJobExecution.endDateTime) }}</div>
      </div>
      <div>
        <div class="text-gray-500 text-xs mb-1">Executed By</div>
        <div>{{ fetchedJobExecution.executionUserId }}</div>
      </div>
      <div>
        <div class="text-gray-500 text-xs mb-1">Job History No</div>
        <div>
          <a @click="goToJobHistory(fetchedJobExecution.jobHistoryNo)"
             class="text-blue-600 hover:underline cursor-pointer">
            {{ fetchedJobExecution.jobHistoryNo }}
          </a>
        </div>
      </div>
    </div>

    <!-- Target Agents -->
    <div>
      <h2 class="text-lg font-semibold mb-2">Target Agents</h2>
      <ul class="space-y-3">
        <li
          v-for="(agent, agentIdx) in fetchedJobExecution.targetAgents"
          :key="agent.ip"
          class="border border-gray-200 rounded-lg p-4 bg-gray-50 max-w-lg"
        >
          <div class="mb-1">
            <a @click="goToAgentDetail(agent.ip, agent.userName)"
               class="text-blue-600 font-medium hover:underline cursor-pointer"
            >
              {{ agent.ip }}
            </a>
          </div>
          <div class="text-xs text-gray-500">
            {{ formatDateTime(agent.startDateTime) }} ~ {{ formatDateTime(agent.endDateTime) }}
          </div>

          <div class="mt-2">
            <code class="block whitespace-pre text-left font-mono">
              {{ getLogByAgentId(agentIdx) }}
            </code>
          </div>
        </li>
      </ul>
    </div>

    <!-- 중단 버튼 -->
    <div v-if="isRunning" class="text-right">
      <button
        @click="pressStopJobExecution"
        class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
      >
        중단하기
      </button>
    </div>
  </div>

  <ConfirmDialog ref="stopJobExecutionDialog" message="Stop the execution?" />
</template>

<style scoped>

</style>
