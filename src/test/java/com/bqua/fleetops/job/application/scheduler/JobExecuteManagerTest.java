package com.bqua.fleetops.job.application.scheduler;

import com.bqua.fleetops.common.type.SchedulePeriod;
import com.bqua.fleetops.helper.AbstractTestContainerTest;
import com.bqua.fleetops.job.application.scheduler.jobexecutor.JobExecutor;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.job.domain.entity.job.enums.TargetAgentType;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobExecution;
import com.bqua.fleetops.job.domain.entity.jobexecution.JobInstance;
import com.bqua.fleetops.job.domain.entity.jobexecution.TargetAgent;
import com.bqua.fleetops.job.domain.entity.jobexecution.enums.JobExecutionStatus;
import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Transactional
class JobExecuteManagerTest extends AbstractTestContainerTest {


    @Autowired
    private JobInstanceStatusHandler jobInstanceStatusHandler;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;


    @Mock
    private JobExecutor jobExecutor;


    private JobExecuteManager jobExecuteManager;

    @BeforeEach
    void setUp() {
        given(jobExecutor.supportType()).willReturn(JobType.SSH);
        doNothing().when(jobExecutor).execute(any(JobInstance.class));

        jobExecuteManager = new JobExecuteManager(List.of(jobExecutor), jobInstanceStatusHandler, jobHistoryRepository);
    }


    @Test
    void shouldFindJobExecutionById() {
        Job job = SshJob.builder()
                .jobId("1")
                .jobName("jobName")
                .jobDescription("job description")
                .active(true)
                .targetAgents(List.of(new TargetAgent(TargetAgentType.AGENT, null, "127.0.0.1", "user1")))
                .period(SchedulePeriod.of("* * * * *"))
                .build();

        JobExecution jobExecution = jobExecuteManager.runJob(job);

        Assertions.assertEquals(JobExecutionStatus.READY, jobExecution.getJobExecutionStatus());
    }

}