package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.common.dto.BaseSearchReq;
import com.bqua.fleetops.common.dto.Page;
import com.bqua.fleetops.common.dto.SortOption;
import com.bqua.fleetops.common.dto.SortDirection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request to search job history based on various criteria")
public class JobHistorySearchReq extends BaseSearchReq {

    private static final Set<String> ALLOWED_SORTS = Set.of("jobHistoryNo");

    @Schema(hidden = true)
    @JsonIgnore
    @Nullable
    private String jobId;

    @Override
    protected Set<String> getAllowedSortFields() {
        return ALLOWED_SORTS;
    }

    @Schema(hidden = true)
    public static JobHistorySearchReq adjust(JobHistorySearchReq req) {
        JobHistorySearchReq adjusted = (req != null) ? req : new JobHistorySearchReq();
        adjusted.adjustAll();
        return adjusted;
    }

    @Override
    protected void adjustFilter() {
        jobId = StringUtils.trim(jobId);
    }

    @Override
    protected SortOption defaultSort() {
        return SortOption.of("jobHistoryNo", SortDirection.DESC);
    }

    @Override
    protected Page defaultPage() {
        return Page.of("0", 10);
    }

    public Pageable toPageable() {
        if (getPage().getMaxPageSize() == null) {
            throw new IllegalStateException("maxPageSize cannot be null");
        }
        return PageRequest.of(getPage().getPageTokenNumber(), getPage().getMaxPageSize(), getSortOption().toSort());
    }
}