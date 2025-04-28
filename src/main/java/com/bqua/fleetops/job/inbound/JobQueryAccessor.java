package com.bqua.fleetops.job.inbound;

import com.bqua.fleetops.common.annotation.AntiCorruptionLayer;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;

import java.util.List;

@AntiCorruptionLayer
public interface JobQueryAccessor {

    List<SimpleJobNameRes> findAgentLinkedJobs(String ip, String userName);

    List<SimpleJobNameRes> findAgentGroupLinkedJobs(String agentGroupId);
}
