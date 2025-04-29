package com.bqua.fleetops.job.application.scheduler;

import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstance;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstanceTargetAgent;
import com.bqua.fleetops.job.domain.logic.JobExecutionFactory;
import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import com.bqua.fleetops.job.domain.outbound.JobExecutionNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobInstanceStatusHandler {

    private final JobExecutionEntityRepository jobExecutionEntityRepository;
    private final JobExecutionNotifier jobExecutionNotifier;
    private final JobExecutionFactory jobExecutionFactory;

    public void create(JobInstance jobInstance) {
        long nextId = jobExecutionFactory.nextId(jobInstance.getJobId());
        jobInstance.setJobExecutionNo(nextId);
        jobExecutionEntityRepository.createJobExecution(jobInstance.toJobExecution());
    }

    public void toReady(JobInstance jobInstance) {
        jobInstance.toReady();
        updateJobStatus(jobInstance);
    }

    public void toStart(JobInstance jobInstance) {
        jobInstance.toStart();
        updateJobStatus(jobInstance);
    }

    public void toComplete(JobInstance jobInstance) {
        jobInstance.toComplete();
        updateJobStatus(jobInstance);
    }

    public void toStop(JobInstance jobInstance) {
        jobInstance.toStop();
        updateJobStatus(jobInstance);
    }

    public void toFail(JobInstance jobInstance) {
        jobInstance.toFail();
        updateJobStatus(jobInstance);
    }

    public void toSkip(JobInstance jobInstance) {
        jobInstance.toSkip();
        updateJobStatus(jobInstance);
    }

    private void updateJobStatus(JobInstance jobInstance) {
        jobExecutionEntityRepository.updateJobExecution(jobInstance.toJobExecution());
        jobExecutionNotifier.sendChange(
                jobInstance.getJobId(),
                jobInstance.getJobExecutionNo(),
                ""
        );
    }

    public void toStart(JobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        targetAgent.toStart();
        updateTargetAgentStatus(jobInstance, targetAgent);
    }

    public void toComplete(JobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        targetAgent.toComplete();
        updateTargetAgentStatus(jobInstance, targetAgent);
    }

    public void toStop(JobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        targetAgent.toStop();
        updateTargetAgentStatus(jobInstance, targetAgent);
    }


    public void toFail(JobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        targetAgent.toFail();
        updateTargetAgentStatus(jobInstance, targetAgent);
    }

    public void toSkip(JobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        targetAgent.toSkip();
        updateTargetAgentStatus(jobInstance, targetAgent);
    }

    private void updateTargetAgentStatus(JobInstance jobInstance, JobInstanceTargetAgent targetAgent) {
        jobExecutionEntityRepository.updateJobExecution(jobInstance.toJobExecution());
        jobExecutionNotifier.sendChange(
                jobInstance.getJobId(),
                jobInstance.getJobExecutionNo(),
                String.valueOf(targetAgent.getIdx())
        );
    }
}