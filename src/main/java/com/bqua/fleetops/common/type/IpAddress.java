package com.bqua.fleetops.common.type;


import com.bqua.fleetops.common.exception.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IpAddress {

    private final String value;

    public void validate() {
        if (isNotValidIp(value)) {
            throw new ValidationException("Invalid IP address: " + value);
        }
    }


    public static boolean isNotValidIp(String ip) {
        return !ip.matches(
                "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$"
        );
    }

    @Override
    public String toString() {
        return value;
    }
}