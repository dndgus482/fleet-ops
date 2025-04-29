package com.bqua.fleetops.job.domain.entity.jobexecution;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionTargetAgentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter @Setter
public class JobInstanceTargetAgent {

    private int idx;

    private String ip;
    private String userName;

    private String agentGroupId;

    private JobExecutionTargetAgentStatus jobExecutionTargetAgentStatus;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    private List<String> logCollection;

    public String getLog() {
        return String.join("", logCollection);
    }

    public void toStart() {
        if (jobExecutionTargetAgentStatus != JobExecutionTargetAgentStatus.CREATED) {
            throw new IllegalStateException("Cannot start job execution target agent " + this);
        }
        startDateTime = ZonedDateTime.now();
        jobExecutionTargetAgentStatus = JobExecutionTargetAgentStatus.STARTED;
    }

    public void toComplete() {
        if (jobExecutionTargetAgentStatus != JobExecutionTargetAgentStatus.STARTED) {
            throw new IllegalStateException("Cannot finish job execution target agent " + this);
        }
        endDateTime = ZonedDateTime.now();
        jobExecutionTargetAgentStatus = JobExecutionTargetAgentStatus.COMPLETED;
    }

    public void toStop() {
        if (jobExecutionTargetAgentStatus != JobExecutionTargetAgentStatus.STARTED) {
            throw new IllegalStateException("Cannot stop job execution target agent " + this);
        }
        endDateTime = ZonedDateTime.now();
        jobExecutionTargetAgentStatus = JobExecutionTargetAgentStatus.STOPPED;
    }

    public void toFail() {
        if (jobExecutionTargetAgentStatus != JobExecutionTargetAgentStatus.STARTED) {
            throw new IllegalStateException("Cannot fail job execution target agent " + this);
        }
        endDateTime = ZonedDateTime.now();
        jobExecutionTargetAgentStatus = JobExecutionTargetAgentStatus.FAILED;
    }

    public void toSkip() {
        if (jobExecutionTargetAgentStatus != JobExecutionTargetAgentStatus.STARTED) {
            throw new IllegalStateException("Cannot skip job execution target agent " + this);
        }
        endDateTime = ZonedDateTime.now();
        jobExecutionTargetAgentStatus = JobExecutionTargetAgentStatus.SKIPPED;
    }

    public JobExecutionTargetAgent toExecution() {
        return JobExecutionTargetAgent.builder()
                .idx(idx)
                .ip(ip)
                .userName(userName)
                .agentGroupId(agentGroupId)
                .jobExecutionTargetAgentStatus(jobExecutionTargetAgentStatus)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .logCollection(logCollection)
                .build();
    }
}
