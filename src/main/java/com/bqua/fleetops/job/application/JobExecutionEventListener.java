package com.bqua.fleetops.job.application;

import com.bqua.fleetops.job.domain.event.JobEvent;
import com.bqua.fleetops.job.domain.outbound.JobExecutionEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobExecutionEventListener {

    private final JobExecutionEntityRepository jobExecutionEntityRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleJobEvent(JobEvent event) {
        if (event.getAction() == JobEvent.JobAction.DELETED) {
            jobExecutionEntityRepository.deleteByJobId(event.getPreviousJob().getJobId());
        }
    }
}