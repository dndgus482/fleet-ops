package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.common.dto.BaseSearchReq;
import com.bqua.fleetops.common.dto.Page;
import com.bqua.fleetops.common.dto.Sort;
import com.bqua.fleetops.common.dto.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class JobSearchReq extends BaseSearchReq {

    private final static Set<String> ALLOWED_SORTS = Set.of("jobId", "jobName");

    @Schema(description = "job name to filter by", example = "job456")
    @Nullable
    private String jobName;

    @Override
    protected Set<String> getAllowedSortFields() {
        return ALLOWED_SORTS;
    }

    public static JobSearchReq adjust(JobSearchReq req) {
        JobSearchReq baseReq = (req != null) ? req : new JobSearchReq();
        baseReq.adjustAll();
        return baseReq;
    }

    @Override
    protected void adjustFilter() {
        jobName = StringUtils.trim(jobName);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.of("jobName", SortDirection.ASC);
    }

    @Override
    protected Page defaultPage() {
        return Page.of(null, Integer.MAX_VALUE);
    }
}
