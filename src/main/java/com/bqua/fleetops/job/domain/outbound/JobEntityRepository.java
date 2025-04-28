package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.domain.entity.job.Job;

import java.util.List;
import java.util.Optional;

public interface JobEntityRepository {

    List<Job> findAll();

    Optional<Job> findJobByJobId(String jobId);

    Job create(Job job);

    Job update(Job job);

    void delete(String jobId);

    List<Job> findByAgentGroupId(String agentGroupId);

    List<Job> findByAgentIpAndUserName(String ip, String userName);

}
