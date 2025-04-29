package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class JobTargetAgentValue {

    @Column(name = "target_agent_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TargetAgentType targetAgentType;

    @Column(name = "agent_group_id")
    private String agentGroupId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_name")
    private String userName;

}
