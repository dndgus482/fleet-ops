package com.bqua.fleetops.job.application.scheduler;

import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstance;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstanceTargetAgent;
import com.bqua.fleetops.job.domain.outbound.JobExecutionNotifier;
import com.bqua.fleetops.job.domain.outbound.LogProcessor;
import com.bqua.fleetops.job.inbound.dto.SshLiveLog;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class JobInstanceLogProcessor implements LogProcessor {

    private final JobInstance jobInstance;
    private final JobInstanceTargetAgent targetAgent;
    private final JobExecutionNotifier jobExecutionNotifier;

    // 스레드 안전하나 그냥 사용
    private final AtomicInteger lineIdx = new AtomicInteger(0);

    @Override
    public void appendLog(String log) {

        targetAgent.getLogCollection().add(log);

        jobExecutionNotifier.sendLog(
                jobInstance.getJobId(),
                jobInstance.getJobExecutionNo(),
                new SshLiveLog(targetAgent.getIdx(), lineIdx.getAndIncrement(), log)
        );
    }
}
