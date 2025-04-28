package com.bqua.fleetops.job.inbound.dto.validation;

import com.bqua.fleetops.job.inbound.dto.TargetAgentReq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TargetAgentValidator implements ConstraintValidator<ValidTargetAgent, TargetAgentReq> {

    @Override
    public boolean isValid(TargetAgentReq targetAgent, ConstraintValidatorContext context) {
        if (targetAgent == null) return false;

        boolean hasAgentGroupName = targetAgent.getAgentGroupId() != null && !targetAgent.getAgentGroupId().isBlank();
        boolean hasIp = targetAgent.getIp() != null && !targetAgent.getIp().isBlank();
        boolean hasUserName = targetAgent.getUserName() != null && !targetAgent.getUserName().isBlank();

        return (hasAgentGroupName && !hasIp && !hasUserName) || (!hasAgentGroupName && hasIp && hasUserName);
    }
}