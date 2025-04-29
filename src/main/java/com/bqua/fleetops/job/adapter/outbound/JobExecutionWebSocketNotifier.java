package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.job.domain.outbound.JobExecutionNotifier;
import com.bqua.fleetops.job.inbound.dto.SshLiveLog;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobExecutionWebSocketNotifier implements JobExecutionNotifier {

    public static final String JOB_EXECUTION_LOG = "/jobs/%s/executions/%d/logs";
    public static final String JOB_EXECUTION_CHANGE = "/jobs/%s/executions/%d/changes";

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendLog(String jobId, Long jobExecutionNo, SshLiveLog sshLiveLog) {
        messagingTemplate.convertAndSend(String.format(JOB_EXECUTION_LOG, jobId, jobExecutionNo), sshLiveLog);
    }

    @Override
    public void sendChange(String jobId, Long jobExecutionNo, String agentIdx) {
        messagingTemplate.convertAndSend(String.format(JOB_EXECUTION_CHANGE, jobId, jobExecutionNo), agentIdx);
    }

}
