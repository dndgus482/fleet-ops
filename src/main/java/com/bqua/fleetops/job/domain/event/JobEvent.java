package com.bqua.fleetops.job.domain.event;

import com.bqua.fleetops.job.domain.entity.job.Job;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JobEvent {
    private final JobAction action;
    private final Job previousJob;
    private final Job job;
    private final LocalDateTime occurredAt;

    private JobEvent(JobAction action, Job previousJob, Job job) {
        this.action = action;
        this.previousJob = previousJob;
        this.job = job;
        this.occurredAt = LocalDateTime.now();
    }

    public static JobEvent created(Job job) {
        return new JobEvent(JobAction.CREATED, null, job);
    }

    public static JobEvent updated(Job previousJob, Job job) {
        return new JobEvent(JobAction.UPDATED, previousJob, job);
    }

    public static JobEvent deleted(Job previousJob) {
        return new JobEvent(JobAction.DELETED, previousJob, null);
    }

    public static JobEvent switchActive(Job job) {
        return new JobEvent(JobAction.SWITCH_ACTIVE, null, job);
    }

    public enum JobAction {
        CREATED, UPDATED, DELETED, SWITCH_ACTIVE
    }
}
