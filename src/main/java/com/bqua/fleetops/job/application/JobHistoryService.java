package com.bqua.fleetops.job.application;

import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import com.bqua.fleetops.job.application.mapper.JobHistoryDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;

    public List<JobHistoryRes> findJobHistoryByJobId(String jobId) {
        return jobHistoryRepository.findJobHistoryByJobId(jobId)
                .stream()
                .map(JobHistoryDtoMapper.INSTANCE::toRes)
                .collect(Collectors.toList());
    }

    public JobHistoryRes findJobHistoryById(String jobId, long jobHistoryNo) {
        return jobHistoryRepository.findJobHistoryById(jobId, jobHistoryNo)
                .map(JobHistoryDtoMapper.INSTANCE::toRes)
                .orElseThrow(NoSuchElementException::new);
    }

}
