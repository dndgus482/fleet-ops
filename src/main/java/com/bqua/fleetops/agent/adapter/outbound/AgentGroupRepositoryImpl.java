package com.bqua.fleetops.agent.adapter.outbound;

import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import com.bqua.fleetops.agent.domain.outbound.AgentGroupRepository;
import com.bqua.fleetops.infrastructure.jpa.entity.AgentGroupEntity;
import com.bqua.fleetops.agent.adapter.outbound.mapper.AgentGroupMapper;
import com.bqua.fleetops.common.exception.BusinessException;
import com.bqua.fleetops.infrastructure.jpa.JpaAgentGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AgentGroupRepositoryImpl implements AgentGroupRepository {

    private final JpaAgentGroupRepository jpaAgentGroupRepository;

    @Override
    public List<AgentGroup> findAll() {
        return jpaAgentGroupRepository.findAllByOrderByAgentGroupName()
                .stream()
                .map(AgentGroupMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AgentGroup> findById(String agentGroupId) {
        return jpaAgentGroupRepository.findByAgentGroupId(agentGroupId)
                .map(AgentGroupMapper.INSTANCE::toDomain);
    }

    @Override
    public AgentGroup create(AgentGroup agentGroup) {
        AgentGroupEntity agentGroupEntity = AgentGroupMapper.INSTANCE.toEntity(agentGroup);
        AgentGroupEntity saved = jpaAgentGroupRepository.save(agentGroupEntity);
        return AgentGroupMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public AgentGroup update(AgentGroup agentGroup) {
        if (StringUtils.isBlank(agentGroup.getAgentGroupId())) {
            throw new IllegalArgumentException("Agent group id must not be blank");
        }
        jpaAgentGroupRepository.findById(agentGroup.getAgentGroupId())
                .orElseThrow(() -> new BusinessException("Agent group id must be exist to update"));
        AgentGroupEntity entity = AgentGroupMapper.INSTANCE.toEntity(agentGroup);
        AgentGroupEntity saved = jpaAgentGroupRepository.save(entity);
        return AgentGroupMapper.INSTANCE.toDomain(saved);
    }


    @Override
    public void delete(String agentGroupId) {
        jpaAgentGroupRepository.deleteById(agentGroupId);
    }
}
