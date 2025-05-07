package com.bqua.fleetops.job.application;

import com.bqua.fleetops.common.dto.SortOption;
import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.helper.AbstractTestContainerTest;
import com.bqua.fleetops.common.dto.Page;
import com.bqua.fleetops.common.dto.SortDirection;
import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import com.bqua.fleetops.job.inbound.dto.JobExecutionRes;
import com.bqua.fleetops.job.inbound.dto.JobExecutionSearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JobExecutionQueryServiceTest extends AbstractTestContainerTest {


    @Autowired
    private JobExecutionQueryService jobExecutionQueryService;

    @Test
    void shouldSearchWhenValidCriteria() {
        var jobExecutionSearchReq = JobExecutionSearchReq.builder()
                .jobId("1")
                .sortOption(SortOption.of("jobExecutionNo", SortDirection.DESC))
                .page(Page.of(null, 10))
                .build();

        PagedResult<JobExecutionRes> result = jobExecutionQueryService.search(jobExecutionSearchReq);


        assertEquals(3, result.getTotalCount());
        assertEquals(3, result.getResults().size());
        assertEquals(3, result.getResults().getFirst().getJobExecutionNo());
    }

    @Test
    void shouldFetchFindByIdWhenExists() {
        // When
        JobExecutionRes jobExecutionRes = jobExecutionQueryService.findById("1", 1L);

        // Then
        assertInstanceOf(JobExecutionRes.class, jobExecutionRes);
        assertEquals("1", jobExecutionRes.getJobId());
        assertEquals("jobName1", jobExecutionRes.getJobName());
        assertEquals(1L, jobExecutionRes.getJobExecutionNo());
        assertEquals(JobExecutionStatus.COMPLETED, jobExecutionRes.getJobExecutionStatus());
        assertEquals(3, jobExecutionRes.getTargetAgents().size());
    }

    @Test
    void shouldThrowWhenFindByIdNotFound() {
        // When & Then
        assertThrows(NotFoundException.class, () -> jobExecutionQueryService.findById("1", 99999L));
    }

}