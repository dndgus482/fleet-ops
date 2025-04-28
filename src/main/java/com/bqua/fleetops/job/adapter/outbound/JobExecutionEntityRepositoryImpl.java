package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import com.bqua.fleetops.infrastructure.jpa.entity.JobExecutionEntity;
import com.bqua.fleetops.job.adapter.outbound.mapper.JobExecutionMapper;
import com.bqua.fleetops.infrastructure.jpa.JpaJobExecutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobExecutionEntityRepositoryImpl implements JobExecutionEntityRepository {

    private final JpaJobExecutionRepository jpaJobExecutionRepository;

    @Override
    public List<JobExecution> findJobExecutionListByJobId(String jobId) {
        List<JobExecutionEntity> jobExecutionEntityList = jpaJobExecutionRepository.findByJobId(jobId);
        return jobExecutionEntityList.stream().map(JobExecutionMapper.INSTANCE::toDomain).toList();
    }

    @Override
    public Optional<JobExecution> findJobExecutionById(String jobId, Long jobExecutionNo) {
        return jpaJobExecutionRepository.findByJobIdAndJobExecutionNo(jobId, jobExecutionNo)
                .map(JobExecutionMapper.INSTANCE::toDomain);
    }

    @Override
    public Map<String, JobExecution> findLastJobExecutions(List<Job> jobs) {
        List<String> jobIds = jobs.stream().map(Job::getJobId).toList();
        return jpaJobExecutionRepository.findLatestExecutionsByJobIds(jobIds)
                .stream()
                .map(JobExecutionMapper.INSTANCE::toDomain)
                .collect(Collectors.toMap(JobExecution::getJobId, Function.identity()));
    }

    @Override
    public JobExecution createJobExecution(JobExecution jobExecution) {
        JobExecutionEntity entity = JobExecutionMapper.INSTANCE.toEntity(jobExecution);
        JobExecutionEntity saved = jpaJobExecutionRepository.save(entity);
        return JobExecutionMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public JobExecution updateJobExecution(JobExecution jobExecution) {
        JobExecutionEntity prevEntity = jpaJobExecutionRepository.findByJobIdAndJobExecutionNo(jobExecution.getJobId(), jobExecution.getJobExecutionNo())
                .orElseThrow(NoSuchElementException::new);
        JobExecutionEntity entity = JobExecutionMapper.INSTANCE.toEntity(jobExecution);
        entity.setJobExecutionId(prevEntity.getJobExecutionId());
        JobExecutionEntity saved = jpaJobExecutionRepository.save(entity);
        return JobExecutionMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public Optional<Long> findLastJobExecutionNoByJobId(String jobId) {
        return jpaJobExecutionRepository.findFirstByJobIdOrderByJobExecutionNoDesc(jobId)
                .map(JobExecutionEntity::getJobExecutionNo);
    }

    @Override
    public void deleteByJobId(String jobId) {
        jpaJobExecutionRepository.deleteByJobId(jobId);
    }

}
