package com.bqua.fleetops.job.application;

import com.bqua.fleetops.helper.AbstractTestContainerTest;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.outbound.JobEntityRepository;
import com.bqua.fleetops.job.inbound.dto.SaveJobReq;
import com.bqua.fleetops.job.inbound.dto.SwitchActiveReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bqua.fleetops.job.domain.JobTestDataFactory.aSaveJobReqSsh;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class JobCommandServiceTest extends AbstractTestContainerTest {
    @Autowired
    private JobCommandService jobCommandService;

    @Autowired
    private JobEntityRepository jobEntityRepository;

    @Test
    void shouldSaveJobSuccessfully() {
        // Given
        String jobName = "save test";
        SaveJobReq toSave = aSaveJobReqSsh();
        toSave.setJobName(jobName);

        // When
        var jobId = jobCommandService.save(toSave);

        // Then
        Optional<Job> savedJob = jobEntityRepository.findJobByJobId(jobId);
        assertTrue(savedJob.isPresent(), "Job should be saved successfully.");
        assertEquals(jobName, savedJob.get().getJobName(), "Job name should match the saved value.");
    }

    @Test
    void shouldUpdateJobSuccessfully() {
        // Given
        String jobName = "update test";
        SaveJobReq toUpdate = aSaveJobReqSsh();
        toUpdate.setJobName(jobName);

        // When
        String jobId = jobCommandService.update("1", toUpdate);

        // Then
        Optional<Job> updatedJob = jobEntityRepository.findJobByJobId(jobId);
        assertTrue(updatedJob.isPresent(), "Job should be updated successfully.");
        assertEquals(jobName, updatedJob.get().getJobName(), "Updated job name should match the new value.");
    }

    @Test
    void shouldDeleteJobSuccessfully() {
        // Given
        String jobId = "1";

        // When
        jobEntityRepository.delete(jobId);

        // Then
        List<Job> savedJobs = jobEntityRepository.findAll();
        assertEquals(1, savedJobs.size(), "Job list size should match expected count after deletion.");
    }

    @Test
    void switchActiveJobSuccessfully() {
        // Given
        String jobId = "1";
        boolean currentActiveState = true;

        // When
        jobCommandService.switchActive(jobId, new SwitchActiveReq(!currentActiveState));

        // Then
        Optional<Job> jobAfterSwitch = jobEntityRepository.findJobByJobId(jobId);
        assertTrue(jobAfterSwitch.isPresent(), "Job should exist after switching active state.");
        assertEquals(!currentActiveState, jobAfterSwitch.get().getActive(), "Job active state should toggle.");
    }

}