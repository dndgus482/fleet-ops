package com.bqua.fleetops.agent.inbound.dto;

import com.bqua.fleetops.common.validator.NoBlankInList;
import com.bqua.fleetops.common.validator.SizeInList;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for saving an agent group including agents and tags.")
public class SaveAgentGroupReq {

    @Schema(description = """
            Name of the agent group
            ⚠️ Maximum 100 characters
            """)
    @NotBlank(message = "agentGroupName must not be blank")
    @Size(max = 100, message = "agentGroupName must be at most 100 characters")
    private String agentGroupName;

    @Schema(description = """
            Optional description for the agent group
            ⚠️ Maximum 1000 characters
            """)
    @Size(max = 1000, message = "agentGroupDescription must be at most 1000 characters")
    private String agentGroupDescription;

    @Schema(description = """
            List of agents belonging to this group
            ⚠️ Must not be empty
            """,
            maxLength = 10
    )
    @Valid
    @Size(max = 100, message = "agent list must contain at most 100 items")
    @NotEmpty(message = "agents must be not be empty")
    private List<AgentReq> agents;

    @Schema(description = """
            Optional list of tags associated with the group
            ⚠️ Can be empty or null
            """,
            maxLength = 10
    )
    @Size(max = 10, message = "tag list must contain at most 10 items")
    @NoBlankInList(message = "Each tag must not be blank")
    @SizeInList(max = 100, message = "Each tag must be at most 100 characters")
    private List<String> tags;

    @Schema(description = """
            Whether the group is active
            ⚠️ Defaults to true if null
            """, example = "true")
    @Nullable
    private Boolean active;
}
