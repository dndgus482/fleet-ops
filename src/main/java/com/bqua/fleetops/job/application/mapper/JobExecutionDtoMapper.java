package com.bqua.fleetops.job.application.mapper;

import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.job.inbound.dto.JobExecutionRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobExecutionDtoMapper {
    JobExecutionDtoMapper INSTANCE = Mappers.getMapper(JobExecutionDtoMapper.class);

    @Mapping(target = "jobName", ignore = true)
    JobExecutionRes toRes(JobExecution jobExecution);
}
