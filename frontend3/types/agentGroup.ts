export interface AgentGroup {
  agentGroupId: string
  agentGroupName: string
  agentGroupDescription: string
  agents: Agent[]
  tags: string[]
  active: boolean
}

export function defaultAgentGroup(): AgentGroup {
  return {
    agentGroupId: '',
    agentGroupName: '',
    agentGroupDescription: '',
    agents: [],
    tags: [],
    active: true,
  }
}

export interface Agent {
  ip: string
  userName: string,
  connected?: boolean,
  log?: string
}

export interface SwitchActiveReq {
  active: boolean
}

export interface AgentConnectionRes {
  ip: string,
  userName: string,
  connected: boolean,
  log: string
}

