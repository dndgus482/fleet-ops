package com.bqua.fleetops.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class AgentValue {

    private String ip;

    private String userName;
}
