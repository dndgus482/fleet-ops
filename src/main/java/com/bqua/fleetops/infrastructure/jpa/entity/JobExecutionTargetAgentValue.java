package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionTargetAgentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Embeddable
@Getter @Setter
public class JobExecutionTargetAgentValue {

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "agent_group_id")
    private String agentGroupId;

    @Column(name = "job_execution_target_agent_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobExecutionTargetAgentStatus jobExecutionTargetAgentStatus;

    @Column(name = "start_date_time")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time")
    private ZonedDateTime endDateTime;

    // TODO: 로그 크기가 클 수 있어 파일 시스템 저장 필요
    @Column(name = "log", columnDefinition = "TEXT")
    private String log;

}
