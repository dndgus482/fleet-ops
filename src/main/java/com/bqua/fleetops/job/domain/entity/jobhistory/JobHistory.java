package com.bqua.fleetops.job.domain.entity.jobhistory;

import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@Builder
@Getter @Setter
public class JobHistory {

    private String jobId;
    private Long jobHistoryNo;

    private String jobName;
    private JobType jobType;

    private Map<String, Object> history;

    private String regUserId;
    private ZonedDateTime regDateTime;
}
