package com.bqua.fleetops.agent.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Response object representing agent connection information")
public record AgentConnectionRes(

        @Schema(description = "IP address of the agent")
        @NotNull
        String ip,

        @Schema(description = "Username of the agent")
        @NotNull
        String userName,

        @Schema(description = "Connection status")
        @NotNull
        boolean connected,

        @Schema(description = "Connection log details")
        @NotNull
        String log

) {
}
