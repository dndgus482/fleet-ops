package com.bqua.fleetops.agent.adapter.outbound.mapper;

import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import com.bqua.fleetops.infrastructure.jpa.entity.AgentGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AgentMapper.class)
public interface AgentGroupMapper {
    AgentGroupMapper INSTANCE = Mappers.getMapper(AgentGroupMapper.class);

    AgentGroup toDomain(AgentGroupEntity entity);

    @Mapping(target = "regUserId", ignore = true)
    @Mapping(target = "regDateTime", ignore = true)
    @Mapping(target = "modUserId", ignore = true)
    @Mapping(target = "modDateTime", ignore = true)
    AgentGroupEntity toEntity(AgentGroup domain);
}
