package com.bqua.fleetops.common.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
    private SortOption sortOption;

    @JsonUnwrapped
    private Page page;

    protected void adjustAll() {
        adjustFilter();
        adjustSort();
        adjustPage();
    }

    protected void adjustSort() {
        this.sortOption = Optional.ofNullable(sortOption)
                .filter(this::isAllowedSort)
                .map(SortOption::adjust)
                .orElseGet(this::defaultSort);
    }

    protected void adjustPage() {
        this.page = (page != null) ? page.adjust() : defaultPage();
    }

    protected abstract void adjustFilter();

    protected abstract Set<String> getAllowedSortFields();

    protected abstract SortOption defaultSort();

    protected abstract Page defaultPage();

    private boolean isAllowedSort(SortOption sortOption) {
        return getAllowedSortFields().contains(sortOption.getSortField());
    }
}