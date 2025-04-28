package com.bqua.fleetops.job.inbound;

import com.bqua.fleetops.job.inbound.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
@Tag(name = "JobExecution", description = "API for managing jobExecutions")
public interface JobExecutionApi {

    @Operation(summary = "Execute a job", description = "Executes the specified job by ID")
    @PostMapping("/jobs/{jobId}/execute")
    JobExecuteRes executeJob(@PathVariable String jobId);

    @Operation(summary = "Search job executions", description = "Search job executions based on given criteria")
    @PostMapping("/jobExecution/search")
    PagedResult<JobExecutionRes> searchJobExecution(@RequestBody(description = "Search filters", required = true) JobExecutionSearchReq req);

    @Operation(summary = "Get job execution by ID", description = "Retrieve details of a specific job execution")
    @GetMapping("/jobs/{jobId}/executions/{jobExecutionNo}")
    JobExecutionRes getJobExecutionById(
            @PathVariable @Valid @NotBlank(message = "jobId must be not be blank") String jobId,
            @PathVariable @Valid Long jobExecutionNo
    );

    @Operation(summary = "Get live logs for job execution", description = "Fetch live logs for the specified job execution")
    @GetMapping("/jobs/{jobId}/executions/{jobExecutionNo}/logs")
    List<SshLiveLog> getLiveLog(@PathVariable String jobId, @PathVariable Long jobExecutionNo);

    @Operation(summary = "Stop job execution", description = "Stops the specified job execution")
    @PostMapping("/jobs/{jobId}/executions/{jobExecutionNo}/stop")
    void stopJobExecution(@PathVariable String jobId, @PathVariable Long jobExecutionNo);
}