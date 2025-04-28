package com.bqua.fleetops.agent.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Agent group response object"
)
public class AgentGroupRes {

    @Schema(description = "ID of the agent group", example = "A1B2C3D4E5")
    @NotNull
    private String agentGroupId;

    @Schema(description = "Name of the agent group", example = "Dev")
    @NotNull
    private String agentGroupName;

    @Schema(description = "Description of the agent group", example = "groups for dev env")
    @NotNull
    private String agentGroupDescription;

    @Schema(description = "List of agents in the group")
    @NotNull
    private List<AgentRes> agents;

    @Schema(description = "List of tags associated with the group")
    @NotNull
    private List<String> tags;

    @Schema(description = "Whether the group is active", example = "true")
    @NotNull
    private boolean active;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AgentRes {

        @Schema(description = "IP address of the agent", example = "192.168.0.1")
        @NotNull
        private String ip;

        @Schema(description = "Username for SSH access", example = "root")
        @NotNull
        private String userName;
    }
}
