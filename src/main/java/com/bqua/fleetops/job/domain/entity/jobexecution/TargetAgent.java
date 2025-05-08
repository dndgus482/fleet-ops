package com.bqua.fleetops.job.domain.entity.jobexecution;

import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TargetAgent {
    private TargetAgentType targetAgentType;
    private String agentGroupId;
    private String ip;
    private String userName;
}
