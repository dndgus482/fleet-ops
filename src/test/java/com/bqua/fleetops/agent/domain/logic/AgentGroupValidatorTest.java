package com.bqua.fleetops.agent.domain.logic;

import com.bqua.fleetops.agent.domain.AgentTestDataFactory;
import com.bqua.fleetops.agent.domain.entity.Agent;
import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import com.bqua.fleetops.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgentGroupValidatorTest {
    @Test
    void shouldPassValidation_whenAgentGroupIsValid() {
        // Given
        AgentGroupValidator agentGroupValidator = new AgentGroupValidator();
        AgentGroup validGroup = AgentTestDataFactory.createAgentGroup()
                .build();

        // When & Then
        assertDoesNotThrow(() -> agentGroupValidator.validate(validGroup), "The valid AgentGroup should pass the validation.");
    }

    @Test
    void shouldThrow_whenDuplicateAgentsExist() {
        // Given
        AgentGroupValidator agentGroupValidator = new AgentGroupValidator();
        AgentGroup invalidGroupWithDuplicates = AgentTestDataFactory.createAgentGroup()
                .agents(List.of(
                        new Agent("192.168.1.1", "validUser"),
                        new Agent("192.168.1.1", "validUser") // Duplicate
                ))
                .build();

        // When & Then
        assertThrows(
                ValidationException.class,
                () -> agentGroupValidator.validate(invalidGroupWithDuplicates),
                "Validation should fail when duplicate agents exist."
        );
    }
}
