package com.bqua.fleetops.job.inbound.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class JobSearchReqJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeserializeJsonToJobSearchReq() throws Exception {
        // language=json
        String json = """
                    {
                        "jobName": "test",
                        "sortField": "jobId",
                        "sortDirection": "ASC",
                        "pageToken": "100",
                        "pageSize": 20
                    }
                """;

        JobSearchReq dto = objectMapper.readValue(json, JobSearchReq.class);
        assertEquals("test", dto.getJobName());
        assertEquals("100", dto.getPage().getPageToken());
        assertEquals("jobId", dto.getSort().getSortField());
    }
}