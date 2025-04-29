package com.bqua.fleetops.job.domain.entity.jobexecution;

import com.bqua.fleetops.job.domain.entity.job.SshJob;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@Getter @Setter
public class SshJobInstance extends JobInstance {

    private List<JobInstanceTargetAgent> targetAgents;

    public SshJob getJob() {
        return (SshJob) super.getJob();
    }

    @Override
    public JobExecution toJobExecution() {
        return JobExecution.builder()
                .jobExecutionId(jobExecutionId)
                .jobId(job.getJobId())
                .jobExecutionNo(jobExecutionNo)
                .jobHistoryNo(jobHistoryNo)
                .jobExecutionStatus(jobExecutionStatus)
                .executionDateTime(executionDateTime)
                .executionUserId(executionUserId)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .targetAgents(targetAgents.stream()
                        .map(JobInstanceTargetAgent::toExecution)
                        .collect(Collectors.toList()))
                .build();
    }
}
