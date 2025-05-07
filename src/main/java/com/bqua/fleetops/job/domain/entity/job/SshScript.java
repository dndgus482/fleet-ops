package com.bqua.fleetops.job.domain.entity.job;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public class SshScript {

    @JsonValue
    private final String value;

    @JsonCreator
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