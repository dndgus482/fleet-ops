package com.bqua.fleetops.job.domain.logic;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JobHistoryFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final JobHistoryRepository jobHistoryRepository;
    public JobHistory create(Job job) {
        long jobHistoryNo = nextId(job.getJobId());
        return from(jobHistoryNo, job);
    }

    private long nextId(String jobId) {
        return jobHistoryRepository.findLastJobHistoryNoByJobId(jobId)
                .map(it -> it + 1)
                .orElse(1L);
    }

    private static JobHistory from(long jobHistoryNo, Job job) {
        return JobHistory.builder()
                .jobId(job.getJobId())
                .jobHistoryNo(jobHistoryNo)
                .jobName(job.getJobName())
                .jobType(job.getJobType())
                .history(toMap(job))
                .regDateTime(ZonedDateTime.now())
                .regUserId("TODO")
                .build();
    }

    private static Map<String, Object> toMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<>() {
        });
    }

}
