package com.bqua.fleetops.job.adapter.outbound.mapper;

import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.infrastructure.jpa.entity.JobExecutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = JobExecutionTargetAgentMapper.class)
public interface JobExecutionMapper {
    JobExecutionMapper INSTANCE = Mappers.getMapper(JobExecutionMapper.class);

    JobExecution toDomain(JobExecutionEntity jobExecutionEntity);

    JobExecutionEntity toEntity(JobExecution job);

}
