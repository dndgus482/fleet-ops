package com.bqua.fleetops.job.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response containing details of the executed job")
public class JobExecuteRes {

    @Schema(description = "Identifier of the job", example = "job123")
    @NotNull
    private String jobId;

    @Schema(description = "Execution number of the job", example = "456")
    @NotNull
    private Long jobExecutionNo;
}