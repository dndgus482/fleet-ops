package com.bqua.fleetops.job.domain.outbound;


public interface SshJobCommandsPort {
    String checkScriptSyntaxLocally(String scriptContent);
    void execute(String ip, String userName, String command, LogProcessor logProcessor);
}