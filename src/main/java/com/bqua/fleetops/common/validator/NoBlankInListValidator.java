package com.bqua.fleetops.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class NoBlankInListValidator implements ConstraintValidator<NoBlankInList, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return true; // 리스트가 null 이거나 비어있는건 이 클래스의 책임이 아님
        }

        for (String element : value) {
            if (StringUtils.isBlank(element)) {
                return false;
            }
        }

        return true;
    }
}