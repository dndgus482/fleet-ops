package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import com.bqua.fleetops.infrastructure.jpa.entity.JobHistoryEntity;
import com.bqua.fleetops.job.adapter.outbound.mapper.JobHistoryMapper;
import com.bqua.fleetops.infrastructure.jpa.JpaJobHistoryRepository;
import com.bqua.fleetops.job.inbound.dto.JobHistorySearchReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JobHistoryRepositoryImpl implements JobHistoryRepository {

    private final JpaJobHistoryRepository jpaJobHistoryRepository;

    @Override
    public Optional<JobHistory> findJobHistoryById(String jobId, long jobHistoryNo) {
        return jpaJobHistoryRepository.findByJobIdAndJobHistoryNo(jobId, jobHistoryNo)
                .map(JobHistoryMapper.INSTANCE::toDomain);
    }

    @Override
    public JobHistory create(JobHistory jobHistory) {
        JobHistoryEntity jobHistoryEntity = JobHistoryMapper.INSTANCE.toEntity(jobHistory);
        JobHistoryEntity saved = jpaJobHistoryRepository.save(jobHistoryEntity);
        return JobHistoryMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public void deleteAllByJobId(String jobId) {
        jpaJobHistoryRepository.deleteByJobId(jobId);
    }

    @Override
    public Optional<JobHistory> findLatestJobHistory(String jobId) {
        return jpaJobHistoryRepository.findFirstByJobIdOrderByRegDateTimeDesc(jobId)
                .map(JobHistoryMapper.INSTANCE::toDomain);
    }

    public Optional<Long> findLastJobHistoryNoByJobId(String jobId) {
        return jpaJobHistoryRepository.findFirstByJobIdOrderByJobHistoryNoDesc(jobId)
                .map(JobHistoryEntity::getJobHistoryNo);
    }

    @Override
    public List<JobHistory> search(JobHistorySearchReq req) {
        return jpaJobHistoryRepository.findAll(req.toPageable())
                .stream()
                .map(JobHistoryMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public long searchCount(JobHistorySearchReq req) {
        return 0;
    }

}
