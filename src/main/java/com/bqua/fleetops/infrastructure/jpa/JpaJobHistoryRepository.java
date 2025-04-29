package com.bqua.fleetops.infrastructure.jpa;

import com.bqua.fleetops.infrastructure.jpa.entity.JobHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaJobHistoryRepository extends JpaRepository<JobHistoryEntity, Long> {
    List<JobHistoryEntity> findByJobId(String jobId);

    Optional<JobHistoryEntity> findFirstByJobIdOrderByRegDateTimeDesc(String jobId);

    Optional<JobHistoryEntity> findByJobIdAndJobHistoryNo(String jobId, Long jobHistoryNo);

    Optional<JobHistoryEntity> findFirstByJobIdOrderByJobHistoryNoDesc(String jobId);

    void deleteByJobId(String jobId);
}