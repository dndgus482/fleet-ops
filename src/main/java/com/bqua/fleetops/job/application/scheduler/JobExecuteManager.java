package com.bqua.fleetops.job.application.scheduler;

import com.bqua.fleetops.job.application.scheduler.jobexecutor.JobExecutor;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstance;
import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.job.domain.logic.JobInstanceFactory;
import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobExecuteManager {

    private static final String SYSTEM_USER = "SYSTEM";

    private final ExecutorService jobExecutorPool = Executors.newFixedThreadPool(10);
    private final List<JobExecutor> jobExecutorList;
    private final Map<JobType, JobExecutor> jobExecutors = new HashMap<>();

    private final JobInstanceStatusHandler jobInstanceStatusHandler;
    private final JobHistoryRepository jobHistoryRepository;

    private final Map<String, RunningJobInstance> runningJobInstances = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        jobExecutorList.forEach(manager -> jobExecutors.put(manager.supportType(), manager));
    }

    public Optional<JobExecution> getJobExecution(String jobExecutionId) {
        return Optional.of(jobExecutionId)
                .map(runningJobInstances::get)
                .map(RunningJobInstance::jobInstance)
                .map(JobInstance::toJobExecution);
    }


    public JobExecution runJob(Job job) {
        String jobId = job.getJobId();
        long latestJobHistoryNo = jobHistoryRepository.findLatestJobHistory(jobId)
                .map(JobHistory::getJobHistoryNo)
                .orElse(0L);
        JobInstance jobInstance = JobInstanceFactory.create(job, latestJobHistoryNo, SYSTEM_USER);
        JobExecutor jobExecutor = jobExecutors.get(job.getJobType());
        jobInstanceStatusHandler.create(jobInstance);

        jobInstanceStatusHandler.toReady(jobInstance);

        CompletableFuture<Void> runningTask = startJobExecutionAsync(jobInstance, jobExecutor);

        runningJobInstances.put(jobInstance.getJobExecutionId(), new RunningJobInstance(jobInstance, runningTask));
        runningTask.whenComplete((_, _) -> runningJobInstances.remove(jobInstance.getJobExecutionId()));

        return jobInstance.toJobExecution();
    }

    private CompletableFuture<Void> startJobExecutionAsync(JobInstance jobInstance, JobExecutor jobExecutor) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.debug("started job jobId: [{}], jobExecutionId: [{}]", jobInstance.getJobId(), jobInstance.getJobExecutionId());
                jobExecutor.execute(jobInstance);
                log.debug("finished job jobId: [{}], jobExecutionId: [{}]", jobInstance.getJobId(), jobInstance.getJobExecutionId());
            } catch (Exception e) {
                jobInstanceStatusHandler.toFail(jobInstance);
                log.debug("failed job jobId: [{}], jobExecutionId: [{}]", jobInstance.getJobId(), jobInstance.getJobExecutionId());
            }
        }, jobExecutorPool);
    }

    public void stopExecution(String jobExecutionId) {
        runningJobInstances.get(jobExecutionId).stop();
    }

    record RunningJobInstance(JobInstance jobInstance, CompletableFuture<Void> runningTask) {
        void stop() {
            runningTask.cancel(true);
        }
    }
}
