package com.bqua.fleetops.infrastructure.jpa;

import com.bqua.fleetops.infrastructure.jpa.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaJobRepository extends JpaRepository<JobEntity, String> {
    Optional<JobEntity> findByJobId(String jobId);

    List<JobEntity> findByTargetAgentsAgentGroupId(String agentGroupId);

    List<JobEntity> findByTargetAgentsIpAndTargetAgentsUserName(String ip, String userName);
}