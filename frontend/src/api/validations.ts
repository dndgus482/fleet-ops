import { z } from 'zod'

const TargetAgentReq = z
  .object({
    targetAgentType: z.enum(['GROUP', 'AGENT']),
    agentGroupId: z.string().optional(),
    ip: z.string().ip().optional(),
    userName: z.string().optional(),
  })
  .passthrough()
const SaveJobReq = z
  .object({
    jobName: z.string().min(1).optional(),
    jobDescription: z.string().optional(),
    jobType: z.literal('SSH'),
    active: z.boolean().optional(),
    targetAgents: z.array(TargetAgentReq).min(1).max(2147483647).optional(),
    period: z.string().min(1).max(100).optional(),
    script: z.string().optional(),
  })
  .passthrough()
const AgentReq = z
  .object({
    ip: z
      .string()
      .min(1)
      .regex(/^(?:[0-9]{1,3}\.){3}[0-9]{1,3}$/),
    userName: z.string().min(0).max(100),
  })
  .partial()
  .passthrough()
const SaveAgentGroupReq = z
  .object({
    agentGroupName: z.string().min(0).max(100),
    agentGroupDescription: z.string().min(0).max(1000),
    agents: z.array(AgentReq).min(1).max(100),
    tags: z.array(z.string()).max(10),
    active: z.boolean(),
  })
  .partial()
  .passthrough()
const SwitchActiveReq = z.object({ active: z.boolean() }).passthrough()
const JobHistorySearchReq = z
  .object({
    sortField: z.string(),
    sortDirection: z.enum(['ASC', 'DESC']),
    pageToken: z.string(),
    maxPageSize: z.number().int(),
  })
  .partial()
  .passthrough()
const JobExecutionSearchReq = z
  .object({
    sortField: z.string(),
    sortDirection: z.enum(['ASC', 'DESC']),
    pageToken: z.string(),
    maxPageSize: z.number().int(),
    jobId: z.string(),
    agentGroupId: z.string(),
    ip: z.string(),
  })
  .partial()
  .passthrough()
const req = z
  .object({
    sortField: z.string(),
    sortDirection: z.enum(['ASC', 'DESC']),
    pageToken: z.string(),
    maxPageSize: z.number().int(),
    jobName: z.string(),
  })
  .partial()
  .passthrough()
const agentReq = z
  .object({
    ip: z
      .string()
      .min(1)
      .regex(/^(?:[0-9]{1,3}\.){3}[0-9]{1,3}$/),
    userName: z.string().min(0).max(100),
  })
  .partial()
  .passthrough()
const TargetAgentRes = z
  .object({
    targetAgentType: z.enum(['GROUP', 'AGENT']),
    agentGroupId: z.string().optional(),
    agentGroupName: z.string().optional(),
    ip: z.string().optional(),
    userName: z.string().optional(),
  })
  .passthrough()
const JobRes = z
  .object({
    jobId: z.string(),
    jobName: z.string(),
    jobDescription: z.string(),
    jobType: z.literal('SSH'),
    active: z.boolean(),
    targetAgents: z.array(TargetAgentRes).optional(),
    period: z.string().optional(),
    script: z.string().optional(),
  })
  .passthrough()
const AgentRes = z
  .object({ ip: z.string(), userName: z.string() })
  .passthrough()
const AgentGroupRes = z
  .object({
    agentGroupId: z.string(),
    agentGroupName: z.string(),
    agentGroupDescription: z.string(),
    agents: z.array(AgentRes),
    tags: z.array(z.string()),
    active: z.boolean(),
  })
  .passthrough()
const JobHistoryRes = z
  .object({
    jobId: z.string(),
    jobHistoryNo: z.number().int(),
    jobName: z.string(),
    jobType: z.literal('SSH'),
    history: z.record(z.object({}).partial().passthrough()),
    regUserId: z.string(),
    regDateTime: z.string().datetime({ offset: true }),
  })
  .passthrough()
const PagedResultJobHistoryRes = z
  .object({
    results: z.array(JobHistoryRes),
    nextPageToken: z.string().optional(),
    totalCount: z.number().int(),
  })
  .passthrough()
const JobExecuteRes = z
  .object({ jobId: z.string(), jobExecutionNo: z.number().int() })
  .passthrough()
const JobExecutionTargetAgentRes = z
  .object({
    ip: z.string(),
    userName: z.string(),
    startDateTime: z.string().datetime({ offset: true }),
    endDateTime: z.string().datetime({ offset: true }),
    log: z.string(),
  })
  .passthrough()
const JobExecutionRes = z
  .object({
    jobId: z.string(),
    jobName: z.string(),
    jobExecutionNo: z.number().int(),
    jobHistoryNo: z.number().int(),
    jobExecutionStatus: z.enum([
      'CREATED',
      'READY',
      'STARTED',
      'COMPLETED',
      'FAILED',
      'STOPPED',
      'SKIPPED',
    ]),
    executionUserId: z.string(),
    executionDateTime: z.string().datetime({ offset: true }),
    startDateTime: z.string().datetime({ offset: true }).optional(),
    endDateTime: z.string().datetime({ offset: true }).optional(),
    targetAgents: z.array(JobExecutionTargetAgentRes),
  })
  .passthrough()
const PagedResultJobExecutionRes = z
  .object({
    results: z.array(JobExecutionRes),
    nextPageToken: z.string().optional(),
    totalCount: z.number().int(),
  })
  .passthrough()
const AgentConnectionRes = z
  .object({
    ip: z.string(),
    userName: z.string(),
    connected: z.boolean(),
    log: z.string(),
  })
  .passthrough()
const SshLiveLog = z
  .object({
    agentIdx: z.number().int(),
    lineIdx: z.number().int(),
    log: z.string(),
  })
  .passthrough()
const JobSearchReq = z
  .object({
    sortField: z.string(),
    sortDirection: z.enum(['ASC', 'DESC']),
    pageToken: z.string(),
    maxPageSize: z.number().int(),
    jobName: z.string(),
  })
  .partial()
  .passthrough()
const PagedResultJobRes = z
  .object({
    results: z.array(JobRes),
    nextPageToken: z.string().optional(),
    totalCount: z.number().int(),
  })
  .passthrough()
const SimpleJobNameRes = z
  .object({ jobId: z.string(), jobName: z.string() })
  .passthrough()

export const schemas = {
  TargetAgentReq,
  SaveJobReq,
  AgentReq,
  SaveAgentGroupReq,
  SwitchActiveReq,
  JobHistorySearchReq,
  JobExecutionSearchReq,
  req,
  agentReq,
  TargetAgentRes,
  JobRes,
  AgentRes,
  AgentGroupRes,
  JobHistoryRes,
  PagedResultJobHistoryRes,
  JobExecuteRes,
  JobExecutionTargetAgentRes,
  JobExecutionRes,
  PagedResultJobExecutionRes,
  AgentConnectionRes,
  SshLiveLog,
  JobSearchReq,
  PagedResultJobRes,
  SimpleJobNameRes,
}
