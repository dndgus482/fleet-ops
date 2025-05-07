package com.bqua.fleetops.job.adapter.inbound;

import com.bqua.fleetops.job.inbound.JobHistoryApi;
import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import com.bqua.fleetops.job.application.JobHistoryService;
import com.bqua.fleetops.job.inbound.dto.JobHistorySearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobHistoryController implements JobHistoryApi {

    private final JobHistoryService jobHistoryService;

    @Override
    public PagedResult<JobHistoryRes> searchJobHistory(String jobId, JobHistorySearchReq req) {
        req.setJobId(jobId);
        return jobHistoryService.search(req);
    }

    @Override
    public JobHistoryRes getJobHistoryById(String jobId, long jobHistoryNo) {
        return jobHistoryService.findJobHistoryById(jobId, jobHistoryNo);
    }

}
