package com.bqua.fleetops.common.infrastructure.ssh;

import com.bqua.fleetops.infrastructure.ssh.dto.LogCallback;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleAppendLogProcessor implements LogCallback {

    private final StringBuilder stringBuilder;

    @Override
    public void appendLog(String log) {
        stringBuilder.append(log);
    }
}
