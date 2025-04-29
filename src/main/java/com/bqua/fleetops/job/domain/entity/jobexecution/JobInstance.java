package com.bqua.fleetops.job.domain.entity.jobexecution;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@SuperBuilder
@Getter @Setter
public abstract class JobInstance {

    protected String jobExecutionId;

    protected Job job;
    protected Long jobExecutionNo;

    protected Long jobHistoryNo;
    protected JobExecutionStatus jobExecutionStatus;

    protected ZonedDateTime executionDateTime;
    protected String executionUserId;
    protected ZonedDateTime startDateTime;
    protected ZonedDateTime endDateTime;

    public String getJobId() {
        return getJob().getJobId();
    }

    public void toReady() {
        if (jobExecutionStatus != JobExecutionStatus.CREATED) {
            throw new IllegalStateException("Cannot pending job execution " + this);
        }
        jobExecutionStatus = JobExecutionStatus.READY;
    }


    public void toStart() {
        if (jobExecutionStatus != JobExecutionStatus.READY) {
            throw new IllegalStateException("Cannot start job execution " + this);
        }
        jobExecutionStatus = JobExecutionStatus.STARTED;
        startDateTime = ZonedDateTime.now();
    }

    public void toComplete() {
        if (jobExecutionStatus != JobExecutionStatus.STARTED) {
            throw new IllegalStateException("Cannot finish job execution " + this);
        }
        jobExecutionStatus = JobExecutionStatus.COMPLETED;
        endDateTime = ZonedDateTime.now();
    }

    public void toStop() {
        if (jobExecutionStatus != JobExecutionStatus.STARTED) {
            throw new IllegalStateException("Cannot stop job execution " + this);
        }
        jobExecutionStatus = JobExecutionStatus.STOPPED;
        endDateTime = ZonedDateTime.now();
    }

    public void toFail() {
        if (jobExecutionStatus != JobExecutionStatus.STARTED) {
            throw new IllegalStateException("Cannot fail job execution " + this);
        }
        jobExecutionStatus = JobExecutionStatus.FAILED;
        endDateTime = ZonedDateTime.now();
    }

    public void toSkip() {
        if (jobExecutionStatus != JobExecutionStatus.STARTED) {
            throw new IllegalStateException("Cannot skip job execution " + this);
        }
        jobExecutionStatus = JobExecutionStatus.SKIPPED;
        endDateTime = ZonedDateTime.now();
    }

    public abstract JobExecution toJobExecution();
}
