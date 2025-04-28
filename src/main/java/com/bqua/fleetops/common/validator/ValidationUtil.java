package com.bqua.fleetops.common.validator;

import com.bqua.fleetops.common.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

public class ValidationUtil {

    public static void shouldNotNull(Object object, String fieldName) {
        if (object == null) {
            throw new ValidationException(fieldName + " must not be null.");
        }
    }

    public static void shouldNotNullOrEmpty(Collection<?> collection, String fieldName) {
        shouldNotNull(collection, fieldName);
        if (CollectionUtils.isEmpty(collection)) {
            throw new ValidationException(fieldName + " must not be empty.");
        }
    }

    public static void shouldNotNullOrEmpty(Map<?, ?> map, String fieldName) {
        shouldNotNull(map, fieldName);
        if (CollectionUtils.isEmpty(map)) {
            throw new ValidationException(fieldName + " must not be empty.");
        }
    }

    public static void shouldNotNullOrEmpty(String str, String fieldName) {
        if (StringUtils.isEmpty(str)) {
            throw new ValidationException(fieldName + " must not be empty.");
        }
    }

    public static void shouldNotBlank(String str, String fieldName) {
        if (StringUtils.isBlank(str)) {
            throw new ValidationException(fieldName + " must not be blank.");
        }
    }


}