package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.inbound.dto.JobExecutionRes;
import com.bqua.fleetops.job.inbound.dto.JobExecutionSearchReq;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface JobExecutionDtoRepository {

    List<JobExecutionRes> search(@NotNull JobExecutionSearchReq spec);

    long searchCount(@NotNull JobExecutionSearchReq spec);

    Optional<JobExecutionRes> findByJobId(@NotNull String jobId, @NotNull Long jobExecutionNo);
}
