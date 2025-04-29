import { subscribe, subscribeJson } from '@/api/ws/wsClient.ts'
import type { SshLiveLog } from '@/types/job.ts'

export function subscribeJobExecutionLogs(jobId: string, jobExecutionNo: number, cb: (msg: SshLiveLog) => void) {
  const topic = `/jobs/${jobId}/executions/${jobExecutionNo}/logs`
  subscribeJson(topic, cb)
}

export function subscribeJobExecutionChange(jobId: string, jobExecutionNo: number, cb: (_: string) => void) {
  const topic = `/jobs/${jobId}/executions/${jobExecutionNo}/changes`
  subscribe(topic, cb)
}



