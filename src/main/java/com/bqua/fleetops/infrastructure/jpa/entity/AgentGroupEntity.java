package com.bqua.fleetops.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "agent_groups")
@Getter @Setter
public class AgentGroupEntity {

    @Id
    @Column(name = "agent_group_id", length = 200, nullable = false)
    private String agentGroupId;

    @Column(name = "agent_group_name", length = 200, nullable = false)
    private String agentGroupName;

    @Column(name = "agent_group_description", length = 2000)
    private String agentGroupDescription;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "agent_groups_agents", joinColumns = @JoinColumn(name = "agent_group_id"))
    @OrderColumn(name = "order_idx")
    @AttributeOverrides({
            @AttributeOverride(name = "ip", column = @Column(name = "ip", length = 200, nullable = false)),
            @AttributeOverride(name = "userName", column = @Column(name = "user_name", length = 200, nullable = false))
    })
    private List<AgentValue> agents;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "agent_groups_tags", joinColumns = @JoinColumn(name = "agent_group_id"))
    private List<String> tags;

    @Column(name = "active", nullable = false)
    @ColumnDefault("false")
    private boolean active;


    @Column(name = "reg_user_id", length = 200, updatable = false)
    private String regUserId;

    @CreationTimestamp
    @Column(name = "reg_date_time", nullable = false, updatable = false)
    private ZonedDateTime regDateTime;

    @Column(name = "mod_user_id", length = 200)
    private String modUserId;

    @UpdateTimestamp
    @Column(name = "mod_date_time", nullable = false)
    private ZonedDateTime modDateTime;

}