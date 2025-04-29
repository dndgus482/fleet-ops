package com.bqua.fleetops.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SizeInListValidator implements ConstraintValidator<SizeInList, List<String>> {

    private int min;
    private int max;

    @Override
    public void initialize(SizeInList constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return true; // null or empty list is not validated here
        }

        for (String element : value) {
            if (element != null && (element.length() < min || element.length() > max)) {
                return false;
            }
        }

        return true;
    }
}