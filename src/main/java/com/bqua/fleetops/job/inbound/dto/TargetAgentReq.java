package com.bqua.fleetops.job.inbound.dto;


import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import com.bqua.fleetops.job.inbound.dto.validation.ValidTargetAgent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ValidTargetAgent
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetAgentReq {
    @NotNull(message = "targetAgentType must not be null")
    private TargetAgentType targetAgentType;
    private String agentGroupId;
    private String ip;
    private String userName;
}
