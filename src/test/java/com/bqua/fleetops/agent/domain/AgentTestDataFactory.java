package com.bqua.fleetops.agent.domain;

import com.bqua.fleetops.agent.domain.entity.Agent;
import com.bqua.fleetops.agent.domain.entity.AgentGroup;

import java.util.List;

public class AgentTestDataFactory {

    public static AgentGroup.AgentGroupBuilder createAgentGroup() {
        return AgentGroup.builder()
                .agentGroupId("ID")
                .agentGroupName("ValidGroupName")
                .agentGroupDescription("Valid description")
                .agents(List.of(new Agent("192.168.1.1", "validUser")))
                .tags(List.of("validTag"))
                .active(true);
    }
}
