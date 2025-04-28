package com.bqua.fleetops.infrastructure.jpa;

import com.bqua.fleetops.infrastructure.jpa.entity.JobExecutionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaJobExecutionRepositoryCustom {
    List<JobExecutionEntity> findLatestExecutionsByJobIds(List<String> jobIds);
}
