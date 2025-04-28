package com.bqua.fleetops.agent.domain.entity;

import com.bqua.fleetops.agent.domain.logic.AgentGroupValidator;
import com.bqua.fleetops.common.util.Random36Generator;
import com.bqua.fleetops.common.validator.Validatable;
import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class AgentGroup implements Validatable<AgentGroupValidator> {

    private String agentGroupId;
    private String agentGroupName;

    private String agentGroupDescription;
    private List<Agent> agents;
    private List<String> tags;
    private boolean active;

    public void generateId() {
        if (StringUtils.isNotBlank(agentGroupId)) {
            throw new IllegalStateException("Cannot generate a new ID when agentGroupId already exists: " + agentGroupId);
        }
        agentGroupId = Random36Generator.generate10Digit();
    }

    @Override
    public void validate(AgentGroupValidator validator) {
        validator.validate(this);
    }
}
