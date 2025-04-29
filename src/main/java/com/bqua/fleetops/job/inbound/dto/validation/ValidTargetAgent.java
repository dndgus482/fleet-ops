package com.bqua.fleetops.job.inbound.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TargetAgentValidator.class)
@Target({ElementType.TYPE}) // 클래스 레벨 검증
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTargetAgent {
    String message() default "Either agentGroupId or (ip & userName) must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}