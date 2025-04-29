package com.bqua.fleetops.job.domain.logic;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.jobexecution.*;
import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionTargetAgentStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.IntStream;

public class JobInstanceFactory {

    public static JobInstance create(Job job, long jobHistoryNo, String userId) {
        if (job instanceof SshJob sshJob) {
            return SshJobInstance.builder()
                    .jobExecutionId(UUID.randomUUID().toString())
                    .job(sshJob)
                    .jobHistoryNo(jobHistoryNo)
                    .jobExecutionStatus(JobExecutionStatus.CREATED)
                    .executionDateTime(ZonedDateTime.now())
                    .executionUserId(userId)
                    .targetAgents(
                            IntStream.range(0, sshJob.getTargetAgents().size())
                                    .mapToObj(i -> {
                                        var target = sshJob.getTargetAgents().get(i);
                                        return JobInstanceTargetAgent.builder()
                                                .idx(i)
                                                .ip(target.getIp())
                                                .userName(target.getUserName())
                                                .agentGroupId(target.getAgentGroupId())
                                                .jobExecutionTargetAgentStatus(JobExecutionTargetAgentStatus.CREATED)
                                                .logCollection(Collections.synchronizedList(new ArrayList<>()))
                                                .build();
                                    })
                                    .toList()
                    )
                    .build();
        }
        throw new IllegalArgumentException("Unsupported job type");
    }

}
