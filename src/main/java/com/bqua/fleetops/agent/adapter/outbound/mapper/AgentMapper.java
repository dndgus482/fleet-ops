package com.bqua.fleetops.agent.adapter.outbound.mapper;

import com.bqua.fleetops.agent.domain.entity.Agent;
import com.bqua.fleetops.common.type.IpAddress;
import com.bqua.fleetops.infrastructure.jpa.entity.AgentValue;
import org.mapstruct.Mapper;

@Mapper
public interface AgentMapper {
    AgentValue toEntity(Agent domain);

    Agent toDomain(AgentValue entity);

    default String map(IpAddress value) {
        return value == null ? null : value.getValue();
    }

    default IpAddress map(String value) {
        return value == null ? null : new IpAddress(value);
    }
}
