package com.bqua.fleetops.job.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Response containing a simplified view of job details with only ID and name")
public record SimpleJobNameRes(

        @Schema(description = "Unique identifier of the job", example = "job123")
        @NotNull
        String jobId,

        @Schema(description = "Name of the job", example = "Daily Backup Job")
        @NotNull
        String jobName
) {
}
