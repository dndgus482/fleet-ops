package com.bqua.fleetops.job.domain;

import com.bqua.fleetops.job.inbound.dto.SaveJobReq;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import com.bqua.fleetops.job.inbound.dto.TargetAgentReq;

import java.util.Arrays;

public class JobTestDataFactory {

    public static SaveJobReq aSaveJobReqSsh() {
        return new SaveJobReq(
                "jobName",
                "jobDescription",
                JobType.SSH,
                true,
                Arrays.asList(
                        new TargetAgentReq(TargetAgentType.AGENT, null, "127.0.0.1", "user1"),
                        new TargetAgentReq(TargetAgentType.GROUP, "something", "127.0.0.1", "user2")
                ),
                "* * * * *",
                "echo hello"
        );
    }
}