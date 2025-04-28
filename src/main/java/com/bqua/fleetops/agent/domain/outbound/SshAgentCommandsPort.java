package com.bqua.fleetops.agent.domain.outbound;


import com.bqua.fleetops.agent.inbound.dto.AgentConnectionRes;

public interface SshAgentCommandsPort {
    AgentConnectionRes connectionTest(String ip, String userName);
}