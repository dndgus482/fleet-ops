package com.bqua.fleetops.job.inbound;

import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import com.bqua.fleetops.job.inbound.dto.JobHistorySearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Tag(name = "JobHistory", description = "API for managing job History")
public interface JobHistoryApi {

    @Operation(summary = "Get job history details", description = "Retrieve the details of a specific job history entry")
    @GetMapping("/jobs/{jobId}/history/{jobHistoryNo}")
    JobHistoryRes getJobHistoryById(@PathVariable String jobId, @PathVariable long jobHistoryNo);

    @Operation(summary = "search job histories", description = "Search job histories")
    @PostMapping("/jobs/{jobId}/history")
    PagedResult<JobHistoryRes> searchJobHistory(@PathVariable String jobId, @RequestBody JobHistorySearchReq req);

}
