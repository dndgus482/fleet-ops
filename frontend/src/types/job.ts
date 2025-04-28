import { type Pageable, type SearchSort, SortDirection } from '@/types/search.ts'

export interface Job {
  jobId: string
  jobName: string
  jobDescription: string
  jobType: 'SSH'
  active: boolean
  targetAgents?: TargetAgent[]
  period?: string
  script?: string
}

export interface TargetAgent {
  targetAgentType: 'GROUP' | 'AGENT'
  agentGroupId?: string
  ip?: string
  userName?: string
}

export function defaultJob(): Job {
  return {
    jobId: '',
    jobName: '',
    jobDescription: '',
    jobType: 'SSH',
    active: true,
    targetAgents: [],
    period: '',
    script: '',
  }
}

export interface SearchJobReq {
  filter: SearchJobFilter
  sort: SearchSort,
  page: Pageable,
}

export type SearchJobFilter = {
  jobName: string
}

export function defaultSearchJobReq(): SearchJobReq {
  return {
    filter: {
      jobName: '',
    },
    sort: {
      sortField: 'jobName',
      sortDirection: SortDirection.ASC,
    },
    page: {
      pageToken: undefined,
      maxPageSize: 30,
    },
  }
}

export interface JobExecuteRes {
  jobId: string
  jobExecutionNo: number
}

export interface JobExecution {
  jobId: string
  jobExecutionNo: number
  jobName: string
  jobHistoryNo: number
  jobExecutionStatus: 'CREATED' | 'READY' | 'STARTED' | 'COMPLETED' | 'FAILED' | 'STOPPED' | 'SKIPPED'
  executionUserId: string
  executionDateTime: string
  startDateTime?: string
  endDateTime?: string
  targetAgents: JobExecutionTargetAgentRes[]
}

export interface JobExecutionTargetAgentRes {
  ip: string
  userName: string
  startDateTime: string
  endDateTime: string
  log?: string
}

export function defaultJobExecution(): JobExecution {
  return {
    jobId: '',
    jobExecutionNo: 0,
    jobName: '',
    jobHistoryNo: 0,
    jobExecutionStatus: 'CREATED',
    executionUserId: '',
    executionDateTime: '',
    startDateTime: '',
    endDateTime: '',
    targetAgents: [],
  }
}

export interface SearchJobExecutionReq {
  filter: SearchJobExecutionFilter
  sort: SearchSort,
  page: Pageable,
}

export type SearchJobExecutionFilter = {
  jobId: string
  agentGroupId: string
  ip: string
}

export function defaultSearchJobExecutionReq(): SearchJobExecutionReq {
  return {
    filter: {
      jobId: '',
      agentGroupId: '',
      ip: '',
    },
    sort: {
      sortField: 'jobId',
      sortDirection: SortDirection.ASC,
    },
    page: {
      pageToken: undefined,
      maxPageSize: 30,
    },
  }
}

export interface JobHistory {
  jobId: string
  jobHistoryNo: number
  jobName: string
  jobType: 'SSH'
  history: Record<string, unknown>
  regUserId: string
  regDateTime: string
}

export function defaultJobHistory(): JobHistory {
  return {
    jobId: '',
    jobHistoryNo: 0,
    jobName: '',
    jobType: 'SSH',
    history: {},
    regUserId: '',
    regDateTime: '',
  }
}

export interface SimpleJobNameRes {
  jobId: string
  jobName: string
}

export interface SshLiveLog {
  agentIdx: number,
  lineIdx: number,
  log: string
}
