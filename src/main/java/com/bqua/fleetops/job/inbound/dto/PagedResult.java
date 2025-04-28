package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResult<T> {

    @Schema(description = "results of search")
    @NotNull
    private List<T> results;

    @Schema(description = "use this token for the next page")
    @Nullable
    private String nextPageToken;

    @Schema(description = "total count of matched search results")
    @NotNull
    private long totalCount;

    public static <T> PagedResult<T> of(List<T> results, Page page, long totalCount) {
        long nextOffset = page.getPageTokenNumber() + results.size();
        String nextToken = nextOffset >= totalCount ? null : String.valueOf(nextOffset);
        return new PagedResult<>(results, nextToken, totalCount);
    }
}
