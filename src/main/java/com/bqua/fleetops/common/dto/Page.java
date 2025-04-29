package com.bqua.fleetops.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    @Schema(description = "null if first search")
    @Nullable
    private String pageToken;

    @Schema(description = "max size to get")
    @Nullable
    private Integer maxPageSize;

    public static Page of(String pageToken, int pageSize) {
        Page page = new Page();
        page.pageToken = pageToken;
        page.maxPageSize = pageSize;
        return page;
    }

    public Page adjust() {
        if (maxPageSize == null || maxPageSize <= 0) {
            maxPageSize = 10;
        }
        return this;
    }

    @Schema(hidden = true)
    public Long getPageTokenNumber() {
        if (StringUtils.isEmpty(pageToken)) {
            return 0L;
        }
        try {
            return Long.parseLong(pageToken);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Schema(hidden = true)
    public String getPageTokenString() {
        if (StringUtils.isEmpty(pageToken)) {
            return null;
        }
        return pageToken;
    }

}
