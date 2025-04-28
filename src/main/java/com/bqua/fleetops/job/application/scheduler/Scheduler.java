package com.bqua.fleetops.job.application.scheduler;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.Schedulable;
import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import com.bqua.fleetops.job.domain.outbound.JobEntityRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ScheduledExecutorService scheduleExecutor = Executors.newSingleThreadScheduledExecutor();
    private final JobExecuteManager jobExecuteManager;
    private final JobEntityRepository jobEntityRepository;
    private final JobExecutionEntityRepository jobExecutionEntityRepository;

    @Getter
    private ZonedDateTime startTime;

    @PostConstruct
    public void init() {
        start();
    }


    public void start() {
        startTime = ZonedDateTime.now();
        log.info("Scheduler started");
        nextSchedule();
    }

    private void nextSchedule() {
        scheduleExecutor.schedule(() -> {
            log.debug("Next scheduled jobs execution started");
            List<Job> registeredJobs = jobEntityRepository.findAll();
            Map<String, ZonedDateTime> lastExecutionDateTimeMap = jobExecutionEntityRepository.findLastJobExecutions(registeredJobs)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getExecutionDateTime()));

            for (Job job : registeredJobs) {
                if (!job.getActive()) {
                    continue;
                }
                if (!(job instanceof Schedulable schedulableJob)) {
                    continue;
                }
                ZonedDateTime lastExecutionDateTime = lastExecutionDateTimeMap.getOrDefault(job.getJobId(), LocalDateTime.MIN.atZone(ZoneId.systemDefault()));
                if (schedulableJob.isTimeToRun(lastExecutionDateTime, ZonedDateTime.now())) {
                    try {
                        jobExecuteManager.runJob(job);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            log.debug("Next scheduled jobs execution finished");
            nextSchedule();
        }, getNextMinuteTimestamp(), TimeUnit.SECONDS);
    }

    private long getNextMinuteTimestamp() {
        return 10;
    }
}
