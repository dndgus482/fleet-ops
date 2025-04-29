package com.bqua.fleetops.agent.adapter.outbound;

import com.bqua.fleetops.agent.domain.outbound.SshAgentCommandsPort;
import com.bqua.fleetops.agent.inbound.dto.AgentConnectionRes;
import com.bqua.fleetops.infrastructure.ssh.SshCommands;
import com.bqua.fleetops.infrastructure.ssh.dto.SshConnectionTestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SshAgentCommandsPortImpl implements SshAgentCommandsPort {

    private final SshCommands sshCommands;

    @Override
    public AgentConnectionRes connectionTest(String ip, String userName) {
        SshConnectionTestResult result = sshCommands.connectionTest(ip, userName);
        return new AgentConnectionRes(ip, userName, result.success(), result.message());
    }
}