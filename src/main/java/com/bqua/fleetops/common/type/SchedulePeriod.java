package com.bqua.fleetops.common.type;


import com.bqua.fleetops.common.exception.ValidationException;
import lombok.Getter;
import org.springframework.scheduling.support.CronExpression;

@Getter
public class SchedulePeriod {

    private final String value;

    public SchedulePeriod(String value) {
        if (!isValid(value)) {
            throw new ValidationException("Invalid Schedule Period: " + value);
        }
        this.value = value;
    }

    public static SchedulePeriod of(String value) {
        return new SchedulePeriod(value);
    }

    public static boolean isValid(String period) {
        if (period == null || period.isBlank()) {
            return false;
        }
        return CronExpression.isValidExpression("0 " + period);
    }

    public void validate() {
        if (!isValid(value)) {
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