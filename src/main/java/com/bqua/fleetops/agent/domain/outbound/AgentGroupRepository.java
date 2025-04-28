package com.bqua.fleetops.agent.domain.outbound;

import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface AgentGroupRepository {

    List<AgentGroup> findAll();

    Optional<AgentGroup> findById(@NotNull String agentGroupId);

    @NotNull AgentGroup create(@NotNull AgentGroup agentGroup);

    @NotNull AgentGroup update(@NotNull AgentGroup agentGroup);

    void delete(@NotNull String agentGroupId);

}
