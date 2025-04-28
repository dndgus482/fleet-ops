package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface JobExecutionEntityRepository {
    List<JobExecution> findJobExecutionListByJobId(String jobId);

    Optional<JobExecution> findJobExecutionById(String jobId, Long jobExecutionNo);

    Map<String, JobExecution> findLastJobExecutions(List<Job> jobs);

    JobExecution createJobExecution(JobExecution jobExecution);

    JobExecution updateJobExecution(JobExecution jobExecution);

    Optional<Long> findLastJobExecutionNoByJobId(String jobId);

    void deleteByJobId(String jobId);
}
