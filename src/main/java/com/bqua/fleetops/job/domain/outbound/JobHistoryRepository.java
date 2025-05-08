package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.job.inbound.dto.JobHistorySearchReq;

import java.util.List;
import java.util.Optional;

public interface JobHistoryRepository {
    Optional<JobHistory> findJobHistoryById(String jobId, long jobHistoryNo);

    JobHistory create(JobHistory jobHistory);

    void deleteAllByJobId(String jobId);

    Optional<JobHistory> findLatestJobHistory(String jobId);

    Optional<Long> findLastJobHistoryNoByJobId(String jobId);

    List<JobHistory> search(JobHistorySearchReq req);

    long searchCount(JobHistorySearchReq req);
}
