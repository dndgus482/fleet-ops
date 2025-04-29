package com.bqua.fleetops.job.inbound.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class SaveJobReqJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeserializeJsonToJobSearchReq() throws Exception {
        // language=json
        String json = """
                    {
                        "jobName": "1",
                        "jobDescription": "2",
                        "jobType": "SSH",
                        "period": "* * * * *",
                        "script": "echo Hello",
                        "targetAgent": []
                    }
                """;

        SaveJobReq dto = objectMapper.readValue(json, SaveJobReq.class);
        assertEquals("1", dto.getJobName());
        assertEquals("echo Hello", dto.getScript());
    }
}