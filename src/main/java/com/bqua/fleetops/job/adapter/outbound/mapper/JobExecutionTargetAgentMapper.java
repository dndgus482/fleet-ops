package com.bqua.fleetops.job.adapter.outbound.mapper;

import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstanceTargetAgent;
import com.bqua.fleetops.infrastructure.jpa.entity.JobExecutionTargetAgentValue;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface JobExecutionTargetAgentMapper {
    JobExecutionTargetAgentValue toEntity(JobInstanceTargetAgent domain);

    List<JobInstanceTargetAgent> toDomainList(List<JobExecutionTargetAgentValue> entities);

    @Mapping(target = "idx", ignore = true)
    @Mapping(target = "logCollection", ignore = true)
    JobInstanceTargetAgent  toDomain(JobExecutionTargetAgentValue entity);

    @AfterMapping
    default void setTargetAgentIndexes(@MappingTarget JobInstanceTargetAgent domain, List<JobExecutionTargetAgentValue> entities) {
        List<JobInstanceTargetAgent> mapped = toDomainList(entities);
        for (int i = 0; i < mapped.size(); i++) {
            mapped.get(i).setIdx(i);
        }
    }

}
