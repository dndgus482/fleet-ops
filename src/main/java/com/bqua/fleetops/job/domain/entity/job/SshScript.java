package com.bqua.fleetops.job.domain.entity.job;

import lombok.Getter;

@Getter
public class SshScript {

    private final String value;

    public SshScript(String value) {
        this.value = value;
    }

    public static SshScript of(String value) {
        return new SshScript(value);
    }

    @Override
    public String toString() {
        return value;
    }
}