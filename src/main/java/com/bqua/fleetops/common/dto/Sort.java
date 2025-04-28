package com.bqua.fleetops.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sort {

    @Schema(description = "sort field")
    @Nullable
    private String sortField;

    @Schema(description = "sort direction")
    @Nullable
    private SortDirection sortDirection;

    public static Sort of(String sortField, SortDirection direction) {
        return new Sort(sortField, direction);
    }

    public Sort adjust() {
        sortDirection = sortDirection != null ? sortDirection : SortDirection.ASC;
        return this;
    }

}

