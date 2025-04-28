package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Response containing details of a target agent")
public class TargetAgentRes {

    @Schema(description = "Type of the target agent", example = "SINGLE_IP")
    @NotNull
    private TargetAgentType targetAgentType;

    @Schema(description = "Group ID of the target agents, if applicable", example = "group123")
    @Nullable
    private String agentGroupId;

    @Schema(description = "Group name of the target agents, if applicable", example = "Server Group 1")
    @Nullable
    private String agentGroupName;

    @Schema(description = "IP address of the target agent", example = "192.168.1.101")
    @Nullable
    private String ip;

    @Schema(description = "Username associated with the target agent", example = "admin")
    @Nullable
    private String userName;
}