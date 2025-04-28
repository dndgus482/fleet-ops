package com.bqua.fleetops.agent.domain.entity;

import com.bqua.fleetops.common.type.IpAddress;
import lombok.Getter;

@Getter
public class Agent {
    private final IpAddress ip;
    private final String userName;

    public Agent(String ip, String userName) {
        this.ip = new IpAddress(ip);
        this.userName = userName;
    }

    public String getIpString() {
        return ip.toString();
    }

    public String getAgentKey() {
        return userName + "@" + ip;
    }

    public void validate() {
        ip.validate();
    }

    @Override
    public String toString() {
        return getAgentKey();
    }
}
