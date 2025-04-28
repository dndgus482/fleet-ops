package com.bqua.fleetops.job.domain.entity.jobexecution;

import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionTargetAgentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public final class JobExecutionTargetAgent {

    private final int idx;
    private final String ip;
    private final String userName;
    private final String agentGroupId;
    private final JobExecutionTargetAgentStatus status;
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;
    private final List<String> logCollection;

    @Builder
    private JobExecutionTargetAgent(
            int idx,
            String ip,
            String userName,
            String agentGroupId,
            JobExecutionTargetAgentStatus status,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            List<String> logCollection) {
        this.idx = idx;
        this.ip = ip;
        this.userName = userName;
        this.agentGroupId = agentGroupId;
        this.status = status;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.logCollection = logCollection != null ?
                List.copyOf(logCollection) : // Read Only
                Collections.emptyList();
    }

    public String getLog() {
        return String.join("", logCollection);
    }
}