package com.bqua.fleetops.job.application;

import com.bqua.fleetops.job.application.mapper.JobHistoryDtoMapper;
import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import com.bqua.fleetops.job.inbound.dto.JobHistoryRes;
import com.bqua.fleetops.job.inbound.dto.JobHistorySearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;

    public PagedResult<JobHistoryRes> search(JobHistorySearchReq req) {
        JobHistorySearchReq.adjust(req);
        List<JobHistoryRes> results = jobHistoryRepository.search(req)
                .stream()
                .map(JobHistoryDtoMapper.INSTANCE::toRes)
                .toList();
        long totalCounts = jobHistoryRepository.searchCount(req);
        return PagedResult.of(results, req.getPage(), totalCounts);
    }

    public JobHistoryRes findJobHistoryById(String jobId, long jobHistoryNo) {
        return jobHistoryRepository.findJobHistoryById(jobId, jobHistoryNo)
                .map(JobHistoryDtoMapper.INSTANCE::toRes)
                .orElseThrow(NoSuchElementException::new);
    }

}
