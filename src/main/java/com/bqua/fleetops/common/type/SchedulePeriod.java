package com.bqua.fleetops.common.type;


import com.bqua.fleetops.common.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.scheduling.support.CronExpression;

@Getter
public class SchedulePeriod {

    @JsonValue
    private final String value;

    @JsonCreator
    public SchedulePeriod(String value) {
        if (isInValid(value)) {
            throw new ValidationException("Invalid Schedule Period: " + value);
        }
        this.value = value;
    }

    public static SchedulePeriod of(String value) {
        return new SchedulePeriod(value);
    }

    public static boolean isInValid(String period) {
        if (period == null || period.isBlank()) {
            return true;
        }
        return !CronExpression.isValidExpression("0 " + period);
    }

    public void validate() {
        if (isInValid(value)) {
            throw new ValidationException("Invalid Schedule Period: " + value);
        }
    }

    public String toCronExpression() {
        return "0 " + value;
    }

    @Override
    public String toString() {
        return value;
    }
}