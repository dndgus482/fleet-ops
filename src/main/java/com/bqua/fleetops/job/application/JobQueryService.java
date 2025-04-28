package com.bqua.fleetops.job.application;

import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.job.domain.outbound.JobDtoRepository;
import com.bqua.fleetops.job.domain.outbound.JobEntityRepository;
import com.bqua.fleetops.job.inbound.JobQueryAccessor;
import com.bqua.fleetops.job.inbound.dto.JobRes;
import com.bqua.fleetops.job.inbound.dto.JobSearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobQueryService implements JobQueryAccessor {

    private final JobDtoRepository jobDtoRepository;
    private final JobEntityRepository jobEntityRepository;

    public PagedResult<JobRes> search(@Nullable JobSearchReq req) {
        req = JobSearchReq.adjust(req);
        List<JobRes> results = jobDtoRepository.search(req);
        long totalCounts = jobDtoRepository.searchCount(req);
        return PagedResult.of(results, req.getPage(), totalCounts);
    }

    public JobRes findById(String jobId) {
        return jobDtoRepository.findByJobId(jobId)
                .orElseThrow(() -> new NotFoundException(jobId));
    }

    @Override
    public List<SimpleJobNameRes> findAgentLinkedJobs(String ip, String userName) {
        return jobEntityRepository.findByAgentIpAndUserName(ip, userName)
                .stream()
                .map(it -> new SimpleJobNameRes(it.getJobId(), it.getJobName()))
                .toList();
    }

    @Override
    public List<SimpleJobNameRes> findAgentGroupLinkedJobs(String agentGroupId) {
        return jobEntityRepository.findByAgentGroupId(agentGroupId)
                .stream()
                .map(job -> new SimpleJobNameRes(job.getJobId(), job.getJobName()))
                .toList();
    }
}
