package com.bqua.fleetops.job.application;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import com.bqua.fleetops.job.domain.outbound.JobEntityRepository;
import com.bqua.fleetops.job.inbound.dto.JobExecuteRes;
import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.job.application.scheduler.JobExecuteManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class JobExecutionCommandService {

    private final JobExecuteManager jobExecuteManager;
    private final JobEntityRepository jobEntityRepository;
    private final JobExecutionEntityRepository jobExecutionEntityRepository;

    public JobExecuteRes executeJob(String jobId) {
        Job job = jobEntityRepository.findJobByJobId(jobId)
                .orElseThrow(() -> new NotFoundException(jobId));
        JobExecution jobExecution = jobExecuteManager.runJob(job);
        return new JobExecuteRes(jobId, jobExecution.getJobExecutionNo());
    }

    public void stopJob(String jobId, long jobExecutionNo) {
        JobExecution jobExecution = jobExecutionEntityRepository.findJobExecutionById(jobId, jobExecutionNo)
                .orElseThrow(() -> new NotFoundException(jobId));

        jobExecuteManager.stopExecution(jobExecution.getJobExecutionId());
    }

}
