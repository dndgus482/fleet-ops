package com.bqua.fleetops.job.application.scheduler.jobexecutor;

import com.bqua.fleetops.job.application.scheduler.JobInstanceLogProcessor;
import com.bqua.fleetops.job.application.scheduler.JobInstanceStatusHandler;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.job.domain.entity.job.SshScript;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstanceTargetAgent;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstance;
import com.bqua.fleetops.job.domain.entity.jobexecution.SshJobInstance;
import com.bqua.fleetops.job.domain.outbound.JobExecutionNotifier;
import com.bqua.fleetops.job.domain.outbound.LogProcessor;
import com.bqua.fleetops.job.domain.outbound.SshJobCommandsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SshJobExecutor implements JobExecutor {

    private final JobExecutionNotifier jobExecutionNotifier;
    private final JobInstanceStatusHandler jobInstanceStatusHandler;
    private final SshJobCommandsPort sshJobCommandsPort;

    @Override
    public JobType supportType() {
        return JobType.SSH;
    }

    @Override
    public void execute(JobInstance inputJobInstance) {
        SshJobInstance jobInstance = (SshJobInstance) inputJobInstance;
        jobInstanceStatusHandler.toStart(jobInstance);
        log.info("Executing job {}", jobInstance.getJob().getJobName());

        try {
            jobInstance.getTargetAgents()
                    .forEach(targetAgent -> executeByAgent(jobInstance, targetAgent));
            jobInstanceStatusHandler.toComplete(jobInstance);
        } catch (Exception e) {
            log.error("Error executing job {}", jobInstance.getJob().getJobName(), e);
            jobInstanceStatusHandler.toFail(jobInstance);
        }
    }

    private void executeByAgent(SshJobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        targetAgent.toStart();
        LogProcessor logProcessor = new JobInstanceLogProcessor(jobInstance, targetAgent, jobExecutionNotifier);
        try {
            executeSshCommand(jobInstance.getJob(), targetAgent, logProcessor);
            targetAgent.toComplete();
        } catch (Exception e) {
            log.error("error", e);
            targetAgent.toFail();
            throw e;
        }
    }

    private void executeSshCommand(SshJob sshJob, JobInstanceTargetAgent targetAgent, LogProcessor logProcessor) {
        String ip = targetAgent.getIp();
        String userName = targetAgent.getUserName();
        SshScript command = sshJob.getScript();
        sshJobCommandsPort.execute(ip, userName, command.toString(), logProcessor);
    }
}
