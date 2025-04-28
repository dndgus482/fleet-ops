package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.common.dto.BaseSearchReq;
import com.bqua.fleetops.common.dto.Page;
import com.bqua.fleetops.common.dto.Sort;
import com.bqua.fleetops.common.dto.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request to search job executions based on various criteria")
public class JobExecutionSearchReq extends BaseSearchReq {

    private static final Set<String> ALLOWED_SORTS = Set.of("executionDateTime", "jobExecutionNo");

    @Schema(description = "Identifier of the job to filter by", example = "job456")
    @Nullable
    private String jobId;

    @Schema(description = "Agent group ID to filter by", example = "group123")
    @Nullable
    private String agentGroupId;

    @Schema(description = "IP address of the agent, nullable if not applicable", example = "192.168.1.1")
    @Nullable
    private String ip;

    @Override
    protected Set<String> getAllowedSortFields() {
        return ALLOWED_SORTS;
    }

    @Schema(hidden = true)
    public static JobExecutionSearchReq adjust(JobExecutionSearchReq req) {
        JobExecutionSearchReq adjusted = (req != null) ? req : new JobExecutionSearchReq();
        adjusted.adjustAll();
        return adjusted;
    }

    @Override
    protected void adjustFilter() {
        jobId = StringUtils.trim(jobId);
        agentGroupId = StringUtils.trim(agentGroupId);
        ip = StringUtils.trim(ip);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.of("executionDateTime", SortDirection.DESC);
    }

    @Override
    protected Page defaultPage() {
        return Page.of("0", 10);
    }
}