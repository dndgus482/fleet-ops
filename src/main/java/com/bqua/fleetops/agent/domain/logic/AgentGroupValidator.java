package com.bqua.fleetops.agent.domain.logic;

import com.bqua.fleetops.agent.domain.entity.Agent;
import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import com.bqua.fleetops.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentGroupValidator {

    public void validate(AgentGroup agentGroup) {
        validateAgents(agentGroup.getAgents());
        validateTags(agentGroup.getTags());
    }

    private void validateAgents(List<Agent> agents) {
        if (hasDuplicateAgents(agents)) {
            throw new ValidationException("Duplicate agent (same IP and username) exists.");
        }
        agents.forEach(Agent::validate);
    }

    private void validateTags(List<String> tags) {
        if (hasDuplicateTags(tags)) {
            throw new ValidationException("Duplicate tag exists.");
        }
    }

    private static boolean hasDuplicateAgents(List<Agent> agents) {
        return agents.stream()
                .map(Agent::getAgentKey)
                .distinct()
                .count() < agents.size();
    }

    private static boolean hasDuplicateTags(List<String> tags) {
        return tags.stream()
                .distinct()
                .count() < tags.size();
    }


}
