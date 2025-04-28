package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.infrastructure.ssh.SshCommands;
import com.bqua.fleetops.job.domain.outbound.LogProcessor;
import com.bqua.fleetops.job.domain.outbound.SshJobCommandsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SshJobCommandsPortImpl implements SshJobCommandsPort {

    private final SshCommands sshCommands;

    @Override
    public String checkScriptSyntaxLocally(String scriptContent) {
        return sshCommands.checkScriptSyntaxLocally(scriptContent);
    }

    @Override
    public void execute(String ip, String userName, String command, LogProcessor logProcessor) {
        sshCommands.execute(ip, userName, command, logProcessor::appendLog);
    }
}
