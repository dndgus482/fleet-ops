package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.common.exception.BusinessException;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.outbound.JobEntityRepository;
import com.bqua.fleetops.infrastructure.jpa.entity.JobEntity;
import com.bqua.fleetops.job.adapter.outbound.mapper.JobMapper;
import com.bqua.fleetops.infrastructure.jpa.JpaJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobEntityRepositoryImpl implements JobEntityRepository {

    private final JpaJobRepository jpaJobRepository;

    @Override
    public List<Job> findAll() {
        return jpaJobRepository.findAll()
                .stream()
                .map(JobMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Job> findJobByJobId(String jobId) {
        return jpaJobRepository.findByJobId(jobId)
                .map(JobMapper.INSTANCE::toDomain);
    }

    @Override
    public Job create(Job job) {
        JobEntity jobEntity = JobMapper.INSTANCE.toEntity(job);
        JobEntity saved = jpaJobRepository.save(jobEntity);
        return JobMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public Job update(Job job) {
        if (StringUtils.isBlank(job.getJobId())) {
            throw new IllegalArgumentException("jobId must not be blank");
        }

        jpaJobRepository.findByJobId(job.getJobId())
                .orElseThrow(() -> new BusinessException("jobId must be exist to update"));

        JobEntity jobEntity = JobMapper.INSTANCE.toEntity(job);
        JobEntity saved = jpaJobRepository.save(jobEntity);
        return JobMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public void delete(String jobId) {
        jpaJobRepository.deleteById(jobId);
    }

    @Override
    public List<Job> findByAgentGroupId(String agentGroupId) {
        return jpaJobRepository.findByTargetAgentsAgentGroupId(agentGroupId)
                .stream()
                .map(JobMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Job> findByAgentIpAndUserName(String ip, String userName) {
        return jpaJobRepository.findByTargetAgentsIpAndTargetAgentsUserName(ip, userName)
                .stream()
                .map(JobMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

}
