package com.bqua.fleetops.common.infrastructure.ssh;

import com.bqua.fleetops.helper.EnvLoader;
import com.bqua.fleetops.infrastructure.ssh.SshCommands;
import com.bqua.fleetops.common.util.HeredocEscaper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SshCommandsTest {

    private static SshCommands ssh;
    private static String testIp;
    private static String account;

    @BeforeAll
    public static void setUp() {
        ssh = new SshCommands();

        EnvLoader.loadFromTestSecretProperties();
        testIp = System.getProperty("TEST_SSH_IP");
        account = System.getProperty("TEST_SSH_ACCOUNT");

        // Validate that required environment variables are set
        assertNotNull(testIp, "Property TEST_SSH_IP is not set. See test-secret-template.properties for more info");
        assertNotNull(account, "Property TEST_SSH_ACCOUNT is not set. See test-secret-template.properties for more info");
    }

    @AfterAll
    public static void tearDown() {
        ssh.shutdown();
    }

    @Test
    void shouldExecuteEchoCommand_whenGivenValidCommand() {
        // Given
        String command = "echo Hello";
        StringBuilder stringBuilder = new StringBuilder();

        // When
        ssh.execute(testIp, account, command, new SimpleAppendLogProcessor(stringBuilder));

        // Then
        String result = String.join("", stringBuilder.toString());
        assertEquals("Hello", result.trim());
    }

    @Test
    void shouldReturnEmptyResult_whenScriptIsEmpty() {
        // Given
        String emptyScript = "";

        // When
        String result = ssh.checkScriptSyntaxLocally(emptyScript);

        // Then
        assertEquals("", result, "Empty script returned an unexpected error.");
    }

    @Test
    void shouldCheckSyntax_whenScriptContainsHeredoc() {
        // Given
        String scriptWithHeredoc = """
                #!/bin/bash
                cat << EOT
                This is a multiline string.
                Variable value: $HOME
                Command execution: `date`
                Escape characters: \\\\ \\$ \\`
                EOT
                """;

        // When
        String escapedScript = HeredocEscaper.escapeForHeredoc(scriptWithHeredoc);
        String result = ssh.checkScriptSyntaxLocally(escapedScript);

        // Then
        assertEquals("", result, "Script with heredoc returned an error.");
    }

    @Test
    void shouldPassSyntaxCheck_whenHeredocContainsInvalidCommand() {
        // Given
        String invalidHeredocScript = """
                #!/bin/bash
                cat << EOT
                Command: `echo "Hello"`
                Invalid command: `if [ -z ];`
                EOT
                """;

        // When
        String result1 = ssh.checkScriptSyntaxLocally(invalidHeredocScript);

        // Then
        assertEquals("", result1, "Syntax check failed even though the script does not execute commands.");
    }

    @Test
    void shouldReturnError_whenScriptIsMalformed() {
        // Given
        String malformedScript = """
                #!/bin/bash
                echo "Start"
                if then
                echo "End"
                """;

        // When
        String result = ssh.checkScriptSyntaxLocally(malformedScript);

        // Then
        assertFalse(result.isEmpty(), "Malformed script did not return an error.");
    }

    @Test
    void shouldReturnError_whenScriptHasUnclosedQuotes() {
        // Given
        String unclosedQuotesScript = """
                #!/bin/bash
                echo "This quote is not closed
                """;

        // When
        String result = ssh.checkScriptSyntaxLocally(unclosedQuotesScript);

        // Then
        assertFalse(result.isEmpty(), "Script with unclosed quotes did not return an error.");
        assertTrue(
                result.contains("quote") || result.contains("unexpected"),
                "Error message should indicate issues with quotes."
        );
    }
}