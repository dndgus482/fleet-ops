package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.job.domain.outbound.JobExecutionDtoRepository;
import com.bqua.fleetops.job.inbound.dto.JobExecutionRes;
import com.bqua.fleetops.job.inbound.dto.JobExecutionSearchReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@Mapper
public interface JobExecutionMapper extends JobExecutionDtoRepository {

    List<JobExecutionRes> search(@Param("spec") JobExecutionSearchReq spec);

    long searchCount(@Param("spec") JobExecutionSearchReq spec);

    Optional<JobExecutionRes> findByJobId(@Param("jobId") @NonNull String jobId, @NonNull Long jobExecutionNo);

}
