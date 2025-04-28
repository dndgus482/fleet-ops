package com.bqua.fleetops.infrastructure.jpa;

import com.bqua.fleetops.infrastructure.jpa.entity.JobExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaJobExecutionRepository extends JpaRepository<JobExecutionEntity, Long>, JpaJobExecutionRepositoryCustom {
    List<JobExecutionEntity> findByJobId(String jobId);

    Optional<JobExecutionEntity> findByJobIdAndJobExecutionNo(String jobId, Long jobExecutionNo);

    Optional<JobExecutionEntity> findFirstByJobIdOrderByJobExecutionNoDesc(String jobId);

    void deleteByJobId(String jobId);
}