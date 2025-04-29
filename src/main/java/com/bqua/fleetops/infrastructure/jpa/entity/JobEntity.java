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
    @Column(name = "job_id", nullable = false)
    private String jobId;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "job_description", length = 2000)
    private String jobDescription;

    @Column(name = "job_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "jobs_target_agents", joinColumns = @JoinColumn(name = "job_id"))
    @OrderColumn(name = "order_idx")
    private List<JobTargetAgentValue> targetAgents;


    @Column(name = "period")
    private String period;

    @Column(name = "script", columnDefinition = "TEXT")
    private String script;

}

