package com.bqua.fleetops.infrastructure.ssh.dto;

@FunctionalInterface
public interface LogCallback {
    void appendLog(String log);
}
