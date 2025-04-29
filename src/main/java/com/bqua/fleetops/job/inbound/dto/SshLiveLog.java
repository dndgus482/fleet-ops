package com.bqua.fleetops.job.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Real-time log information for SSH job execution")
public class SshLiveLog {

    @Schema(description = "Index of the target agent", example = "1")
    @NotNull
    private int agentIdx;

    @Schema(description = "Index of the log line", example = "15")
    @NotNull
    private int lineIdx;

    @Schema(description = "Log content from SSH execution", example = "Job execution started successfully")
    @NotNull
    private String log;
}