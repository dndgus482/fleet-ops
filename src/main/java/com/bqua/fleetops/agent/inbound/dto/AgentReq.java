package com.bqua.fleetops.agent.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = """
        Request object containing agent connection information
        """)
public class AgentReq {

    @Schema(description = """
            IP address of the agent in IPv4 format
            """,
            example = "192.168.0.1"
    )
    @NotBlank(message = "ip must not be blank")
    @Size(min = 7, max = 15, message = "ip must be between 7 and 15 characters")
    private String ip;

    @Schema(description = """
            Username for SSH login (1–100 characters allowed)
            ⚠️ Maximum 100 characters
            """,
            example = "root",
            maxLength = 100
    )
    @NotBlank(message = "userName must not be blank")
    @Size(max = 100, message = "userName must be at most 100 characters")
    private String userName;
}
