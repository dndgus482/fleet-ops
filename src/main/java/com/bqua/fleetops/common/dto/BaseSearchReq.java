package com.bqua.fleetops.common.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseSearchReq {

    @JsonUnwrapped
    @Schema(description = "sort results")
    @Nullable
    private Sort sort;

    @JsonUnwrapped
    @Schema(description = "pageable for search")
    @Nullable
    private Page page;

    protected void adjustAll() {
        adjustFilter();
        adjustSort();
        adjustPage();
    }

    protected void adjustSort() {
        this.sort = Optional.ofNullable(sort)
                .filter(this::isAllowedSort)
                .map(Sort::adjust)
                .orElseGet(this::defaultSort);
    }

    protected void adjustPage() {
        this.page = (page != null) ? page.adjust() : defaultPage();
    }

    protected abstract void adjustFilter();

    protected abstract Set<String> getAllowedSortFields();

    protected abstract Sort defaultSort();

    protected abstract Page defaultPage();

    private boolean isAllowedSort(Sort sort) {
        return getAllowedSortFields().contains(sort.getSortField());
    }
}