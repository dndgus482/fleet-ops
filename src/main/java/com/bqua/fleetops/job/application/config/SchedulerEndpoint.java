package com.bqua.fleetops.job.application.config;

import com.bqua.fleetops.job.application.scheduler.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Endpoint(id = "custom-scheduler")
@RequiredArgsConstructor
public class SchedulerEndpoint {
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final AtomicInteger runningJobsCount = new AtomicInteger(0);
    private final Scheduler scheduler;
    private ZonedDateTime lastRunTime;
    private final LinkedList<String> recentExecutions = new LinkedList<>();

    @ReadOperation
    public SchedulerStatus checkSchedulerStatus() {
        return new SchedulerStatus(
                isRunning.get(),
                runningJobsCount.get(),
                lastRunTime,
                recentExecutions
        );
    }

    @WriteOperation
    public void updateSchedulerStatus(@Selector String status) {
        if ("start".equalsIgnoreCase(status)) {
            isRunning.set(true);
        } else if ("stop".equalsIgnoreCase(status)) {
            isRunning.set(false);
        }
    }

    public void jobStarted() {
        runningJobsCount.incrementAndGet();
        lastRunTime = ZonedDateTime.now();
    }

    public void jobFinished(String jobName) {
        runningJobsCount.decrementAndGet();
        recentExecutions.addFirst(jobName + " completed at " + ZonedDateTime.now());
        if (recentExecutions.size() > 5) {
            recentExecutions.removeLast(); // 최근 5개까지만 저장
        }
    }

    public record SchedulerStatus(
            boolean isRunning,
            int runningJobsCount,
            ZonedDateTime lastRunTime,
            List<String> recentExecutions
    ) {}
}