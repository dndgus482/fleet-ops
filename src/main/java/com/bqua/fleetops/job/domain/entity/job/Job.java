package com.bqua.fleetops.job.domain.entity.job;

import com.bqua.fleetops.common.util.Random36Generator;
import com.bqua.fleetops.common.validator.Validatable;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.logic.JobValidator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@SuperBuilder
@Getter
@Setter
public abstract class Job implements Validatable<JobValidator> {

    private String jobId;

    private String jobName;

    private String jobDescription;

    private boolean active;

    public abstract JobType getJobType();

    public void generateId() {
        if (StringUtils.isNotEmpty(jobId)) {
            throw new IllegalStateException("Cannot generate a new ID when jobId already exists: " + jobId);
        }
        jobId = Random36Generator.generate10Digit();
    }

}
