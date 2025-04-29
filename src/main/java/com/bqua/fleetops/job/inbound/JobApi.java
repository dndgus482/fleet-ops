package com.bqua.fleetops.job.inbound;

import com.bqua.fleetops.job.inbound.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Tag(name = "Job", description = "API for managing jobs")
public interface JobApi {

    @Operation(summary = "Retrieve all jobs", description = "Search for jobs based on criteria")
    @GetMapping("/job/search")
    PagedResult<JobRes> searchJob(JobSearchReq req);

    @Operation(summary = "Get job by ID", description = "Retrieve specific job details")
    @GetMapping("/jobs/{jobId}")
    JobRes getJobById(@PathVariable String jobId);

    @Operation(summary = "Create a new job", description = "Add a new job to the system")
    @PostMapping(value = "/jobs")
    JobRes createJob(@RequestBody @Valid SaveJobReq req);

    @Operation(summary = "Update a job", description = "Modify an existing job")
    @PutMapping("/jobs/{jobId}")
    JobRes updateJob(@PathVariable @Valid @NotBlank(message = "jobId must be not be blank") String jobId,
                     @RequestBody @Valid SaveJobReq req);

    @Operation(summary = "Delete a job", description = "Remove a job from the system")
    @DeleteMapping("/jobs/{jobId}")
    void deleteJobById(@PathVariable String jobId);

    @Operation(summary = "Switch job active status", description = "Enable or disable the active status of a job")
    @PostMapping("/jobs/{jobId}/switchActive")
    void switchActiveJob(@PathVariable String jobId, @RequestBody SwitchActiveReq switchActiveReq);
}