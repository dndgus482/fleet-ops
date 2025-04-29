package com.bqua.fleetops.job.application.mapper;

import com.bqua.fleetops.common.type.SchedulePeriod;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.job.domain.entity.job.SshScript;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.jobexecution.TargetAgent;
import com.bqua.fleetops.job.inbound.dto.SaveJobReq;
import com.bqua.fleetops.job.inbound.dto.TargetAgentReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Optional;

@Mapper
public interface JobDtoMapper {
    JobDtoMapper INSTANCE = Mappers.getMapper(JobDtoMapper.class);

    default Job toSsh(SaveJobReq dto) {
        return SshJob.builder()
                .jobName(dto.getJobName())
                .jobDescription(dto.getJobDescription())
                .active(dto.getActive() == null || dto.getActive())
                .targetAgents(Optional.ofNullable(dto.getTargetAgents())
                        .orElse(Collections.emptyList())
                        .stream().map(this::toTargetAgent).toList())
                .period(SchedulePeriod.of(dto.getPeriod()))
                .script(SshScript.of(dto.getScript()))
                .build();
    }

    default Job toDomain(SaveJobReq dto) {
        if (dto.getJobType() == JobType.SSH) {
            return toSsh(dto);
        }
        throw new IllegalArgumentException("Invalid type: " + dto.getClass().getName());
    }

    default TargetAgent toTargetAgent(TargetAgentReq dto) {
        return new TargetAgent(dto.getTargetAgentType(), dto.getAgentGroupId(), dto.getIp(), dto.getUserName());
    }

}
