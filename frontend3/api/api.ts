import { AgentGroupApi, JobApi, JobExecutionApi, JobHistoryApi } from '@/generated'

export const agentGroupApi = new AgentGroupApi()

export const jobApi = new JobApi()

export const jobExecutionApi = new JobExecutionApi()

export const jobHistoryApi = new JobHistoryApi()

export const wsTopics = {
  jobExecutionLogs: (jobId: string, jobExecutionNo: number) =>
    `/jobs/${jobId}/executions/${jobExecutionNo}/logs`,

  jobExecutionChanges: (jobId: string, jobExecutionNo: number) =>
    `/jobs/${jobId}/executions/${jobExecutionNo}/changes`,
}
