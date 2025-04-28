package com.bqua.fleetops.job.domain.entity.job;

import com.bqua.fleetops.common.type.SchedulePeriod;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public interface Schedulable {
    SchedulePeriod getPeriod();

    default boolean isTimeToRun(ZonedDateTime lastExecutionDateTime, ZonedDateTime now) {
        return !getNextExecutionTime(lastExecutionDateTime).isAfter(now);
    }

    default ZonedDateTime getNextExecutionTime(ZonedDateTime lastExecutionDateTime) {
        CronExpression cronExpression = CronExpression.parse(getPeriod().toCronExpression());

        return Optional.ofNullable(cronExpression.next(lastExecutionDateTime))
                .orElse(LocalDateTime.MAX.atZone(ZoneId.systemDefault()));
    }
}
