package com.bqua.fleetops.job.adapter.outbound.mapper;

import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.infrastructure.jpa.entity.JobHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobHistoryMapper {
    JobHistoryMapper INSTANCE = Mappers.getMapper(JobHistoryMapper.class);

    JobHistory toDomain(JobHistoryEntity jobHistoryEntity);

    @Mapping(target = "seq", ignore = true)
    JobHistoryEntity toEntity(JobHistory jobHistory);

}
