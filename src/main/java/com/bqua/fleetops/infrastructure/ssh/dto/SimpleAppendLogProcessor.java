package com.bqua.fleetops.infrastructure.ssh.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleAppendLogProcessor implements LogCallback {

    private final StringBuilder stringBuilder;

    @Override
    public void appendLog(String log) {
        stringBuilder.append(log);
    }
}
