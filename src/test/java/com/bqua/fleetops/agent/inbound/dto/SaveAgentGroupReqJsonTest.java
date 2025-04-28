package com.bqua.fleetops.agent.inbound.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JsonTest
class SaveAgentGroupReqJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeserializeJsonToSaveAgentGroupReq() throws Exception {
        // language=json
        String json = """
                {
                  "agentGroupName": "test",
                  "agentGroupDescription": "desc",
                  "agents": [
                    { "ip": "127.0.0.1", "userName": "user1" }
                  ],
                  "tags": ["tag1", "tag2"],
                  "active": true
                }
                """;

        SaveAgentGroupReq req = objectMapper.readValue(json, SaveAgentGroupReq.class);

        assertEquals("test", req.getAgentGroupName());
        assertEquals("desc", req.getAgentGroupDescription());

        assertEquals(1, req.getAgents().size());
        assertEquals("127.0.0.1", req.getAgents().getFirst().getIp());
        assertEquals("user1", req.getAgents().getFirst().getUserName());

        assertEquals(2, req.getTags().size());
        assertEquals("tag1", req.getTags().getFirst());
        assertEquals("tag2", req.getTags().get(1));

        assertEquals(Boolean.TRUE, req.getActive());
    }
}