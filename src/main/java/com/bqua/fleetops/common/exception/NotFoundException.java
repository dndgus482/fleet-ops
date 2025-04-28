package com.bqua.fleetops.common.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("Could not find id " + id);
    }

    public NotFoundException(long id) {
        super("Could not find id " + id);
    }
}
