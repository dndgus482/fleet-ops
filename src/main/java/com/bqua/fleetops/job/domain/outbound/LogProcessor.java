package com.bqua.fleetops.job.domain.outbound;

@FunctionalInterface
public interface LogProcessor {

    /**
     * 로그를 처리하는 메서드
     * @param log 로그 메시지
     */
    void appendLog(String log);
}
