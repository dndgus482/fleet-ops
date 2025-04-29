package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_executions", uniqueConstraints = {@UniqueConstraint(columnNames = {"job_id", "job_execution_no"})})
@Getter @Setter
public class JobExecutionEntity {

    @Id
    @Column(name = "job_execution_id")
    private String jobExecutionId;

    @Column(name = "job_id", nullable = false, updatable = false)
    private String jobId;

    @Column(name = "job_execution_no", nullable = false, updatable = false)
    private Long jobExecutionNo;

    @Column(name = "job_history_no", nullable = false)
    private Long jobHistoryNo;

    @Column(name = "job_execution_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobExecutionStatus jobExecutionStatus;

    @Column(name = "execution_user_id", updatable = false)
    private String executionUserId;

    @Column(name = "execution_date_time")
    private ZonedDateTime executionDateTime;

    @Column(name = "start_date_time")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time")
    private ZonedDateTime endDateTime;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "job_executions_target_agents", joinColumns = @JoinColumn(name = "job_execution_id"))
    @OrderColumn(name = "order_idx")
    private List<JobExecutionTargetAgentValue> targetAgents;

    @PrePersist
    public void generateId() {
        if (jobExecutionId == null) {
            jobExecutionId = UUID.randomUUID().toString();
        }
    }

}