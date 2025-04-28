package com.bqua.fleetops.job.application;

import com.bqua.fleetops.helper.AbstractTestContainerTest;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.common.dto.Page;
import com.bqua.fleetops.common.dto.Sort;
import com.bqua.fleetops.common.dto.SortDirection;
import com.bqua.fleetops.job.inbound.dto.JobRes;
import com.bqua.fleetops.job.inbound.dto.JobSearchReq;
import com.bqua.fleetops.job.inbound.dto.PagedResult;
import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class JobQueryServiceTest extends AbstractTestContainerTest {

    @Autowired
    private JobQueryService jobQueryService;

    @Test
    void shouldSearchWhenValidCriteria() {
        // Given
        JobSearchReq req = JobSearchReq.builder()
                .jobName("jobName")
                .sort(Sort.of("jobName", SortDirection.ASC))
                .page(Page.of("0", 10))
                .build();

        // When
        PagedResult<JobRes> pagedResult = jobQueryService.search(req);

        // Then
        List<JobRes> jobResList = pagedResult.getResults();
        assertEquals(2, jobResList.size());

        // Given updated search criteria
        req.setJobName("Name1");

        // When
        pagedResult = jobQueryService.search(req);

        // Then
        jobResList = pagedResult.getResults();
        assertEquals(1, jobResList.size());
    }

    @Test
    void shouldFetchJobByIdWhenExists() {
        // When
        JobRes jobRes = jobQueryService.findById("1");

        // Then
        assertInstanceOf(JobRes.class, jobRes);
        assertEquals("1", jobRes.getJobId());
        assertEquals("jobName1", jobRes.getJobName());
        assertEquals("jobDescription1", jobRes.getJobDescription());
        assertEquals(JobType.SSH, jobRes.getJobType());
        assertTrue(jobRes.getActive());
    }

    @Test
    void shouldThrowWhenJobNotFound() {
        // When & Then
        assertThrows(NotFoundException.class, () -> jobQueryService.findById("X"));
    }


    @Test
    void shouldFetchAgentGroupLinkedJobsWhenExists() {
        // When
        List<SimpleJobNameRes> jobList = jobQueryService.findAgentGroupLinkedJobs("1");

        // Then
        assertNotNull(jobList);
        assertEquals(1, jobList.size());
        assertEquals("jobName1", jobList.getFirst().jobName());
    }

    @Test
    void emptyListAgentGroupLinkedJobsWhenNotExists() {
        // When
        List<SimpleJobNameRes> jobList = jobQueryService.findAgentGroupLinkedJobs("X");

        // Then
        assertNotNull(jobList);
        assertEquals(0, jobList.size());
    }


    @Test
    void shouldFetchAgentLinkedJobsWhenExists() {
        // Given
        var ip = "192.168.0.1";
        var userName = "user1";

        // When
        List<SimpleJobNameRes> jobList = jobQueryService.findAgentLinkedJobs(ip, userName);

        // Then
        assertNotNull(jobList);
        assertEquals(1, jobList.size());
        assertEquals("jobName1", jobList.getFirst().jobName());
    }
}