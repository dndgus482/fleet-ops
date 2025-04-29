package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.inbound.dto.JobRes;
import com.bqua.fleetops.job.inbound.dto.JobSearchReq;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface JobDtoRepository {

    List<JobRes> search(@NotNull JobSearchReq spec);

    long searchCount(@NotNull JobSearchReq spec);

    Optional<JobRes> findByJobId(String jobId);
}
