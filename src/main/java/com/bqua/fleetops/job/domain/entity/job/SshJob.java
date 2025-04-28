package com.bqua.fleetops.job.domain.entity.job;

import com.bqua.fleetops.common.type.SchedulePeriod;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.jobexecution.TargetAgent;
import com.bqua.fleetops.job.domain.logic.JobValidator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter @Setter
public class SshJob extends Job implements Schedulable {

    private List<TargetAgent> targetAgents;
    private SchedulePeriod period;
    private SshScript script;


    @Override
    public JobType getJobType() {
        return JobType.SSH;
    }

    @Override
    public void validate(JobValidator validator) {
        validator.validate(this);
    }
}
