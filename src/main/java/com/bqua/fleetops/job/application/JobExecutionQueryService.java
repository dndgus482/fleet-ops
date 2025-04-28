package com.bqua.fleetops.job.application;

import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.job.application.scheduler.JobExecuteManager;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecutionTargetAgent;
import com.bqua.fleetops.job.domain.outbound.JobExecutionDtoRepository;
import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import com.bqua.fleetops.job.inbound.dto.JobExecutionRes;
import com.bqua.fleetops.job.inbound.dto.JobExecutionSearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import com.bqua.fleetops.job.inbound.dto.SshLiveLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class JobExecutionQueryService {

    private final JobExecutionDtoRepository jobExecutionDtoRepository;
    private final JobExecutionEntityRepository jobExecutionEntityRepository;
    private final JobExecuteManager jobExecuteManager;

    public PagedResult<JobExecutionRes> search(JobExecutionSearchReq req) {
        req = JobExecutionSearchReq.adjust(req);
        List<JobExecutionRes> results = jobExecutionDtoRepository.search(req);
        long totalCounts = jobExecutionDtoRepository.searchCount(req);
        return PagedResult.of(results, req.getPage(), totalCounts);
    }

    public JobExecutionRes findById(String jobId, Long jobExecutionNo) {
        return jobExecutionDtoRepository.findByJobId(jobId, jobExecutionNo)
                .orElseThrow(() -> new NotFoundException(jobExecutionNo));
    }

    public List<SshLiveLog> getLiveLog(String jobId, long jobExecutionNo) {
        JobExecution jobExecution = jobExecutionEntityRepository.findJobExecutionById(jobId, jobExecutionNo)
                .orElseThrow(() -> new NotFoundException(jobId));

        List<JobExecutionTargetAgent> jobInstanceTargetAgents = jobExecuteManager.getJobExecution(jobExecution.getJobExecutionId())
                .map(JobExecution::getTargetAgents)
                .orElse(Collections.emptyList());

        return jobInstanceTargetAgents
                .stream()
                .map(targetAgent -> IntStream.range(0, targetAgent.getLogCollection().size())
                        .mapToObj(i ->
                                new SshLiveLog(targetAgent.getIdx(), i, targetAgent.getLogCollection().get(i)))
                        .toList())
                .flatMap(Collection::stream)
                .toList();
    }

}
