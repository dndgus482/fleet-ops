package com.bqua.fleetops.job.application.mapper;

import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobHistoryDtoMapper {
    JobHistoryDtoMapper INSTANCE = Mappers.getMapper(JobHistoryDtoMapper.class);

    JobHistoryRes toRes(JobHistory jobExecution);
}
