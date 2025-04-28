package com.bqua.fleetops.job.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Schema(description = "Response containing details about a target agent involved in job execution")
public class JobExecutionTargetAgentRes {

    @Schema(description = "IP address of the agent", example = "192.168.1.1")
    @NotNull
    private String ip;

    @Schema(description = "Username of the agent", example = "user1")
    @NotNull
    private String userName;


    @Schema(description = "Date and time when the agent started the execution", example = "2023-08-04T12:35:00Z")
    @NotNull
    private ZonedDateTime startDateTime;

    @Schema(description = "Date and time when the agent finished the execution", example = "2023-08-04T12:50:00Z")
    @NotNull
    private ZonedDateTime endDateTime;

    @Schema(description = "Log details specific to this agent's execution", example = "Execution completed successfully")
    @NotNull
    private String log;
}