package com.bqua.fleetops.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoBlankInListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoBlankInList {

    String message() default "The list must not contain blank or null elements";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}