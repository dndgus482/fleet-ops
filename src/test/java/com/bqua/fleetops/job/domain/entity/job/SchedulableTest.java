package com.bqua.fleetops.job.domain.entity.job;

import com.bqua.fleetops.common.type.SchedulePeriod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

class SchedulableTest {

    @Test
    void isTimeToRun() {
        var schedulable = new Schedulable() {
            @Override
            public SchedulePeriod getPeriod() {
                return SchedulePeriod.of("1 * * * *");
            }
        };

        boolean timeToRun = schedulable.isTimeToRun(ZonedDateTime.now(), ZonedDateTime.now());
        Assertions.assertFalse(timeToRun);

    }

    @Test
    void getNextExecutionTime() {
        var schedulable = new Schedulable() {
            @Override
            public SchedulePeriod getPeriod() {
                return SchedulePeriod.of("1 * * * *");
            }
        };

        ZonedDateTime nextExecutionTime = schedulable.getNextExecutionTime(ZonedDateTime.now());
        Assertions.assertTrue(nextExecutionTime.isAfter(ZonedDateTime.now()));
    }
}