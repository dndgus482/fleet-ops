package com.bqua.fleetops.job.adapter.outbound.mapper;

import com.bqua.fleetops.common.type.SchedulePeriod;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.infrastructure.jpa.entity.JobEntity;
import com.bqua.fleetops.job.domain.entity.job.SshScript;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TargetAgentMapper.class)
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    SshJob toSshJobDomain(JobEntity jobEntity);

    default Job toDomain(JobEntity jobEntity) {
        if (jobEntity.getJobType() == JobType.SSH) {
            return toSshJobDomain(jobEntity);
        } else {
            return null;
        }
    }

    JobEntity toEntity(SshJob job);

    default JobEntity toEntity(Job job) {
        if (job instanceof SshJob) {
            return toEntity((SshJob) job);
        } else {
            return null;
        }
    }


    default String schedulePeriod(SchedulePeriod value) {
        return value == null ? null : value.getValue();
    }

    default SchedulePeriod schedulePeriod(String value) {
        return value == null ? null : new SchedulePeriod(value);
    }

    default String sshScript(SshScript value) {
        return value == null ? null : value.getValue();
    }

    default SshScript sshScript(String value) {
        return value == null ? null : new SshScript(value);
    }

}
