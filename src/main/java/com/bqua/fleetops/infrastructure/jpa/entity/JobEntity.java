package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "jobs")
@Getter @Setter
public class JobEntity {

    @Id
    private String jobId;

    @Column(length = 200, nullable = false)
    private String jobName;

    @Column(length = 2000)
    private String jobDescription;

    @Column(name = "job_type", updatable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "jobs_target_agents", joinColumns = @JoinColumn(name = "job_id"))
    @OrderColumn(name = "order_idx")
    @AttributeOverrides({
            @AttributeOverride(name = "targetAgentType", column = @Column(name = "target_agent_type", length = 200, nullable = false)),
            @AttributeOverride(name = "agentGroupId", column = @Column(name = "agent_group_id", length = 200)),
            @AttributeOverride(name = "ip", column = @Column(name = "ip", length = 200)),
            @AttributeOverride(name = "userName", column = @Column(name = "user_name", length = 200))
    })
    private List<JobTargetAgentValue> targetAgents;


    @Column(length = 200)
    private String period;

    @Column(columnDefinition = "TEXT")
    private String script;

}

