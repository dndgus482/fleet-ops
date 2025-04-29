package com.bqua.fleetops.job.adapter.inbound;

import com.bqua.fleetops.job.inbound.JobExecutionApi;
import com.bqua.fleetops.job.inbound.dto.*;
import com.bqua.fleetops.job.application.JobExecutionCommandService;
import com.bqua.fleetops.job.application.JobExecutionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobExecutionController implements JobExecutionApi {

    private final JobExecutionCommandService jobExecutionCommandService;
    private final JobExecutionQueryService jobExecutionQueryService;

    @Override
    public PagedResult<JobExecutionRes> searchJobExecution(JobExecutionSearchReq req) {
        return jobExecutionQueryService.search(req);
    }

    @Override
    public JobExecutionRes getJobExecutionById(String jobId, Long jobExecutionNo) {
        return jobExecutionQueryService.findById(jobId, jobExecutionNo);
    }

    @Override
    public JobExecuteRes executeJob(String jobId) {
        return jobExecutionCommandService.executeJob(jobId);
    }

    @Override
    public void stopJobExecution(String jobId, Long jobExecutionNo) {
        jobExecutionCommandService.stopJob(jobId, jobExecutionNo);
    }

    @Override
    public List<SshLiveLog> getLiveLog(String jobId, Long jobExecutionNo) {
        return jobExecutionQueryService.getLiveLog(jobId, jobExecutionNo);
    }

}
