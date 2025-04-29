package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response containing detailed information about a specific job execution.")
public class JobExecutionRes {

    @Schema(description = "Identifier of the job", example = "job123")
    @NotNull
    private String jobId;

    @Schema(description = "Name of the job", example = "Data Processing Job")
    @NotNull
    private String jobName;

    @Schema(description = "Execution number of the job execution", example = "789")
    @NotNull
    private Long jobExecutionNo;

    @Schema(description = "Job history number linked to the execution", example = "456")
    @NotNull
    private Long jobHistoryNo;

    @Schema(description = "Current status of the job execution")
    @NotNull
    private JobExecutionStatus jobExecutionStatus;

    @Schema(description = "User ID of the user who executed the job", example = "user789")
    @NotNull
    private String executionUserId;

    @Schema(description = "Date and time when the job was executed", example = "2023-08-04T12:34:56Z")
    @NotNull
    private ZonedDateTime executionDateTime;

    @Schema(description = "Date and time when the execution started", example = "2023-08-04T12:35:00Z")
    private ZonedDateTime startDateTime;

    @Schema(description = "Date and time when the execution ended", example = "2023-08-04T12:50:00Z")
    private ZonedDateTime endDateTime;

    @Schema(description = "List of target agents involved in this job execution")
    @NotNull
    private List<JobExecutionTargetAgentRes> targetAgents;
}