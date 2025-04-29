package com.bqua.fleetops.job.adapter.inbound;

import com.bqua.fleetops.job.application.JobCommandService;
import com.bqua.fleetops.job.application.JobQueryService;
import com.bqua.fleetops.job.inbound.JobApi;
import com.bqua.fleetops.job.inbound.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobController implements JobApi {

    private final JobCommandService jobCommandService;
    private final JobQueryService jobQueryService;

    public PagedResult<JobRes> searchJob(JobSearchReq req) {
        return jobQueryService.search(req);
    }

    public JobRes getJobById(String jobId) {
        return jobQueryService.findById(jobId);
    }

    public JobRes createJob(SaveJobReq req) {
        var jobId = jobCommandService.save(req);
        return jobQueryService.findById(jobId);
    }

    public JobRes updateJob(String jobId, SaveJobReq req) {
        jobCommandService.update(jobId, req);
        return jobQueryService.findById(jobId);
    }

    public void deleteJobById(String jobId) {
        jobCommandService.deleteById(jobId);
    }

    public void switchActiveJob(String jobId, SwitchActiveReq switchActiveReq) {
        jobCommandService.switchActive(jobId, switchActiveReq);
    }

}
