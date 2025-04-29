package com.bqua.fleetops.infrastructure.jpa;

import com.bqua.fleetops.infrastructure.jpa.entity.AgentGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAgentGroupRepository extends JpaRepository<AgentGroupEntity, String> {
    Optional<AgentGroupEntity> findByAgentGroupId(String agentGroupId);

    List<AgentGroupEntity> findAllByOrderByAgentGroupName();
}