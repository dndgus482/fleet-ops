package com.bqua.fleetops.job.adapter.outbound.mapper;

import com.bqua.fleetops.job.domain.entity.jobexecution.TargetAgent;
import com.bqua.fleetops.infrastructure.jpa.entity.JobTargetAgentValue;
import org.mapstruct.Mapper;

@Mapper
public interface TargetAgentMapper {
    JobTargetAgentValue toEntity(TargetAgent domain);

    TargetAgent toDomain(JobTargetAgentValue entity);
}
