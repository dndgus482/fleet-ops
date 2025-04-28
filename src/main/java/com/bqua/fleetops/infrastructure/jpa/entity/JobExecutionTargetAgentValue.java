package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionTargetAgentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Embeddable
@Getter @Setter
public class JobExecutionTargetAgentValue {

    @Column(length = 200, nullable = false)
    private String ip;

    @Column(length = 200, nullable = false)
    private String userName;

    @Column(length = 200)
    private String agentGroupId;

    private JobExecutionTargetAgentStatus status;

    @Column
    private ZonedDateTime startDateTime;

    @Column
    private ZonedDateTime endDateTime;

    // TODO: 로그 크기가 클 수 있어 파일 시스템 저장 필요
    @Column(columnDefinition = "TEXT")
    private String log;

}
