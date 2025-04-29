package com.bqua.fleetops.job.inbound;

import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "JobHistory", description = "API for managing job History")
public interface JobHistoryApi {

    @Operation(summary = "Get job history by job ID", description = "Retrieve the history of job executions based on job ID")
    @GetMapping("/jobs/{jobId}/history")
    List<JobHistoryRes> getJobHistoryByJobId(@PathVariable String jobId);

    @Operation(summary = "Get job history details", description = "Retrieve the details of a specific job history entry")
    @GetMapping("/jobs/{jobId}/history/{jobHistoryNo}")
    JobHistoryRes getJobHistoryById(@PathVariable String jobId, @PathVariable long jobHistoryNo);
}
