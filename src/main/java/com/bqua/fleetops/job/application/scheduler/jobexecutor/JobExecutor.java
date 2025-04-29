package com.bqua.fleetops.job.application.scheduler.jobexecutor;

import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstance;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;

public interface JobExecutor {
    JobType supportType();
    void execute(JobInstance jobInstance);
}
