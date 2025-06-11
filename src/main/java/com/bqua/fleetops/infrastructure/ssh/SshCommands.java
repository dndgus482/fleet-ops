package com.bqua.fleetops.infrastructure.ssh;

import com.bqua.fleetops.common.util.HeredocEscaper;
import com.bqua.fleetops.infrastructure.ssh.dto.LogCallback;
import com.bqua.fleetops.infrastructure.ssh.dto.SimpleAppendLogProcessor;
import com.bqua.fleetops.infrastructure.ssh.dto.SshConnectionTestResult;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SshCommands {

    private final static int PORT = 22;
    private final SshClient sshClient;

    private final KeyPairLoader keyPairLoader;

    public SshCommands(KeyPairLoader keyPairLoader) {
        this.keyPairLoader = keyPairLoader;
        sshClient = SshClient.setUpDefaultClient();
        sshClient.start();
    }

    @PreDestroy
    public void shutdown() {
        if (sshClient != null && sshClient.isOpen()) {
            sshClient.stop();
        }
    }

    public SshConnectionTestResult connectionTest(String ip, String userName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            execute(ip, userName, "echo connection-ok", new SimpleAppendLogProcessor(stringBuilder));
            String result = String.join("", stringBuilder);

            if ("connection-ok\n".equals(result)) {
                return new SshConnectionTestResult(true, "");
            } else {
                return new SshConnectionTestResult(false, "Unexpected output: " + result);
            }
        } catch (Exception e) {
            log.warn("SSH connection test failed", e);
            return new SshConnectionTestResult(false, e.getMessage() + '\n' + e.getCause().getMessage());
        }
    }

    //TODO: 통신 연결 실패 처리, 로그 복구 등
    public void execute(String ip, String userName, String command, LogCallback logCallback) {
        try (ClientSession session = connect(ip, userName)) {
            try (ChannelExec channel = session.createExecChannel(command)) {
                channel.open().verify();
                try (InputStream in = channel.getInvertedOut()) {
                    byte[] buf = new byte[4096];
                    int read;
                    while ((read = in.read(buf)) != -1) {
                        String chunk = new String(buf, 0, read, StandardCharsets.UTF_8);
                        logCallback.appendLog(chunk);
                    }
                }
                channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(10));
            }
        } catch (IOException e) {
            throw new RuntimeException("SSH command execution failed", e);
        }
    }

    private ClientSession connect(String ip, String username) throws IOException {
        ConnectFuture connectFuture = sshClient.connect(username, ip, PORT).verify(10, TimeUnit.SECONDS);
        ClientSession session = connectFuture.getSession();
        session.addPublicKeyIdentity(keyPairLoader.getKeyPair());
        session.auth().verify(10, TimeUnit.SECONDS);
        return session;
    }

    /**
     * bash -n -s 명령을 사용하여 로컬에서 쉘 스크립트의 문법을 검사합니다.
     *
     * @param scriptContent 검사할 스크립트 내용
     * @return 문법 검사 결과(오류 메시지 또는 성공 시 빈 문자열)
     */
    public String checkScriptSyntaxLocally(String scriptContent) {
        try {
            // bash -n -s 명령 실행 (-s 옵션 추가)
            ProcessBuilder pb = new ProcessBuilder("bash", "-n", "-s");
            pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
            pb.redirectError(ProcessBuilder.Redirect.PIPE);

            Process process = pb.start();

            // 스크립트 내용을 프로세스에 전달
            try (OutputStream os = process.getOutputStream()) {
                // HeredocEscaper 사용하여 heredoc 이스케이프 처리
                String escapedScript = HeredocEscaper.escapeForHeredoc(scriptContent);
                os.write(escapedScript.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // 오류 메시지 수집
            StringBuilder errorOutput = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    errorOutput.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return ""; // 문법 오류 없음
            } else {
                return errorOutput.toString();
            }

        } catch (Exception e) {
            return "문법 검사 실패: " + e.getMessage();
        }
    }
}
