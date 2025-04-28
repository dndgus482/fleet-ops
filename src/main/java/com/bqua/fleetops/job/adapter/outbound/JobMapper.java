package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.job.domain.outbound.JobDtoRepository;
import com.bqua.fleetops.job.inbound.dto.JobRes;
import com.bqua.fleetops.job.inbound.dto.JobSearchReq;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface JobMapper extends JobDtoRepository {

    List<JobRes> search(@Param("spec") @NotNull JobSearchReq spec);

    long searchCount(@Param("spec") @NotNull JobSearchReq spec);

    Optional<JobRes> findByJobId(@Param("jobId") String jobId);

}
