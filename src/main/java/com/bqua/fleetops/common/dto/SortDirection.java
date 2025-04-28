package com.bqua.fleetops.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    private final String direction;
}
