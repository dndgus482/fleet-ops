package com.bqua.fleetops.common.dto;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ErrorResponse {
    private final String error;
    private final String timestamp;

    public ErrorResponse(String error) {
        this.error = error;
        this.timestamp = ZonedDateTime.now().toString();
    }
}
