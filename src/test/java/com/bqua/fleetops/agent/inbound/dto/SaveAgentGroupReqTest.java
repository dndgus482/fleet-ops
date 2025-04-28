package com.bqua.fleetops.agent.inbound.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveAgentGroupReqTest {

    Validator validator;

    @BeforeEach
    void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }
    
    @Test
    public void shouldFailValidation_whenAgentGroupNameIsNull() {
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                null,
                "description",
                List.of(new AgentReq("192.168.1.1", "validUser")),
                List.of("validTag"),
                true
        );

        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldFailValidation_whenAgentsListIsEmpty() {
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                "validGroupName",
                "description",
                List.of(),
                List.of("validTag"),
                true
        );

        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldFailValidation_whenTagsListExceedsMaxSize() {
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                "validGroupName",
                "description",
                List.of(new AgentReq("192.168.1.1", "validUser")),
                List.of("tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10", "tag11"),
                true
        );

        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldFailValidation_whenAgentUserNameIsTooLong() {
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                "validGroupName",
                "description",
                List.of(new AgentReq("192.168.1.1", "a".repeat(101))),
                List.of("validTag"),
                true
        );

        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldFailValidation_whenTagIsBlank() {
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                "test group",
                "desc",
                List.of(new AgentReq("127.0.0.1", "user")),
                List.of(" "),
                true
        );

        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldPassValidation_whenAllFieldsAreValid() {
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                "validGroupName",
                "Valid description",
                List.of(new AgentReq("192.168.1.1", "validUser")),
                List.of("validTag"),
                true
        );

        var violations = validator.validate(req);
        assertTrue(violations.isEmpty());
    }
}