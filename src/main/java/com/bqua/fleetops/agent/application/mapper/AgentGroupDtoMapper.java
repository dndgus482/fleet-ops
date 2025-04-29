package com.bqua.fleetops.agent.application.mapper;

import com.bqua.fleetops.agent.domain.entity.Agent;
import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import com.bqua.fleetops.agent.inbound.dto.SaveAgentGroupReq;
import com.bqua.fleetops.common.type.IpAddress;
import com.bqua.fleetops.agent.inbound.dto.AgentGroupRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AgentGroupDtoMapper {
    AgentGroupDtoMapper INSTANCE = Mappers.getMapper(AgentGroupDtoMapper.class);

    AgentGroupRes toRes(AgentGroup agentGroup);

    default String map(IpAddress ip) {
        return ip == null ? null : ip.getValue();
    }

    default AgentGroup saveAgentGroupReq(SaveAgentGroupReq dto) {
        return AgentGroup.builder()
                .agentGroupName(dto.getAgentGroupName())
                .agentGroupDescription(dto.getAgentGroupDescription())
                .agents(dto.getAgents().stream().map(it -> new Agent(it.getIp(), it.getUserName())).toList())
                .tags(dto.getTags())
                .active(dto.getActive() == null || dto.getActive())
                .build();
    }
}
