package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(description = "Response containing details of a job's execution history")
public class JobHistoryRes {

    @Schema(description = "Unique identifier of the job", example = "job123")
    @NotNull
    private String jobId;

    @Schema(description = "Unique number for the job history", example = "1001")
    @NotNull
    private Long jobHistoryNo;

    @Schema(description = "Name of the job", example = "Data Processing Job")
    @NotNull
    private String jobName;

    @Schema(description = "Type of the job", example = "SCHEDULED")
    @NotNull
    private JobType jobType;

    @Schema(description = "Additional history details of the job represented as key-value pairs")
    @NotNull
    private Map<String, Object> history;

    @Schema(description = "User ID of the person who registered the job", example = "adminUser")
    @NotNull
    private String regUserId;

    @Schema(description = "Date and time when the job was registered", example = "2023-08-04T12:35:00Z")
    @NotNull
    private ZonedDateTime regDateTime;
}