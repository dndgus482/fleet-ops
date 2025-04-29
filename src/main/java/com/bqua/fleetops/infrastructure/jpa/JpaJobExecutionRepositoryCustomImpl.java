package com.bqua.fleetops.infrastructure.jpa;

import com.bqua.fleetops.infrastructure.jpa.entity.JobExecutionEntity;

import java.util.Collections;
import java.util.List;

public class JpaJobExecutionRepositoryCustomImpl implements JpaJobExecutionRepositoryCustom {
    public List<JobExecutionEntity> findLatestExecutionsByJobIds(List<String> jobIds) {
        // TODO
        return Collections.emptyList();
    }
}
