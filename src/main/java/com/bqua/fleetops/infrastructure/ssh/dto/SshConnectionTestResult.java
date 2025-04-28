package com.bqua.fleetops.infrastructure.ssh.dto;

public record SshConnectionTestResult(
        boolean success,
        String message // 성공이면 출력, 실패면 에러 메시지
) {}