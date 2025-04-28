package com.bqua.fleetops.job.adapter.inbound;

import com.bqua.fleetops.job.inbound.JobHistoryApi;
import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import com.bqua.fleetops.job.application.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobHistoryController implements JobHistoryApi {

    private final JobHistoryService jobHistoryService;

    @Override
    public List<JobHistoryRes> getJobHistoryByJobId(String jobId) {
        return jobHistoryService.findJobHistoryByJobId(jobId);
    }

    @Override
    public JobHistoryRes getJobHistoryById(String jobId, long jobHistoryNo) {
        return jobHistoryService.findJobHistoryById(jobId, jobHistoryNo);
    }

}
