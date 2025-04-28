package com.bqua.fleetops.common.validator;

public interface Validatable<T> {
    void validate(T validator);
}