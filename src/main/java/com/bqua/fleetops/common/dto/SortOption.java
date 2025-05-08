package com.bqua.fleetops.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortOption {

    @Schema(description = "sort field")
    @Nullable
    private String sortField;

    @Schema(description = "sort direction")
    @Nullable
    private SortDirection sortDirection;

    public static SortOption of(String sortField, SortDirection direction) {
        return new SortOption(sortField, direction);
    }

    public SortOption adjust() {
        sortDirection = sortDirection != null ? sortDirection : SortDirection.ASC;
        return this;
    }

    public org.springframework.data.domain.Sort toSort() {
        if (sortDirection != null) {
            return Sort.by(Sort.Direction.fromString(sortDirection.name()), sortField);
        }
        throw new IllegalArgumentException("Sort direction is null");
    }

}

