package com.bqua.fleetops.job.domain.entity.jobexecution;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public class JobExecution {

    private final String jobExecutionId;

    private final String jobId;
    private final Long jobExecutionNo;

    private final Long jobHistoryNo;
    private final JobExecutionStatus jobExecutionStatus;

    private final ZonedDateTime executionDateTime; // Include Pending
    private final String executionUserId;
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;

    private final List<JobExecutionTargetAgent> targetAgents;


    @Builder
    private JobExecution(
            String jobExecutionId,
            String jobId,
            Long jobExecutionNo,
            Long jobHistoryNo,
            JobExecutionStatus jobExecutionStatus,
            ZonedDateTime executionDateTime,
            String executionUserId,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            List<JobExecutionTargetAgent> targetAgents
    ) {
        this.jobExecutionId = jobExecutionId;
        this.jobId = jobId;
        this.jobExecutionNo = jobExecutionNo;
        this.jobHistoryNo = jobHistoryNo;
        this.jobExecutionStatus = jobExecutionStatus;
        this.executionDateTime = executionDateTime;
        this.executionUserId = executionUserId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.targetAgents = targetAgents != null ?
                List.copyOf(targetAgents) : // Read Only
                Collections.emptyList();
    }

}
