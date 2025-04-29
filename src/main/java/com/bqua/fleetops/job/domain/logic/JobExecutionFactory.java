package com.bqua.fleetops.job.domain.logic;

import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobExecutionFactory {

    private final JobExecutionEntityRepository jobExecutionEntityRepository;

    public long nextId(String jobId) {
        return jobExecutionEntityRepository.findLastJobExecutionNoByJobId(jobId)
                .map(it -> it + 1)
                .orElse(1L);
    }

}
