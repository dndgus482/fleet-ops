package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class JobTargetAgentValue {

    @Enumerated(EnumType.STRING)
    private TargetAgentType targetAgentType;

    private String agentGroupId;

    private String ip;

    private String userName;

}
