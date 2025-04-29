package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;

import java.util.List;
import java.util.Optional;

public interface JobHistoryRepository {
    List<JobHistory> findJobHistoryByJobId(String jobId);

    Optional<JobHistory> findJobHistoryById(String jobId, long jobHistoryNo);

    JobHistory create(JobHistory jobHistory);

    void deleteAllByJobId(String jobId);

    Optional<JobHistory> findLatestJobHistory(String jobId);

    Optional<Long> findLastJobHistoryNoByJobId(String jobId);
}
