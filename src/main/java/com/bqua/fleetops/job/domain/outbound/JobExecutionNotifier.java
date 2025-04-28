package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.inbound.dto.SshLiveLog;

public interface JobExecutionNotifier {
    void sendLog(String jobId, Long jobExecutionNo, SshLiveLog sshLiveLog);

    void sendChange(String jobId, Long jobExecutionNo, String agentIdx);
}
