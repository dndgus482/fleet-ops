package com.bqua.fleetops.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class AgentValue {

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "user_name", nullable = false)
    private String userName;
}
