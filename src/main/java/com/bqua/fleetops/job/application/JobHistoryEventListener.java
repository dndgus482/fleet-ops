package com.bqua.fleetops.job.application;

import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.job.domain.entity.jobhistory.JobHistory;
import com.bqua.fleetops.job.domain.event.JobEvent;
import com.bqua.fleetops.job.domain.logic.JobHistoryFactory;
import com.bqua.fleetops.job.domain.outbound.JobHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobHistoryEventListener {

    private final JobHistoryFactory jobHistoryFactory;
    private final JobHistoryRepository jobHistoryRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleJobEvent(JobEvent event) {
        if (event.getAction() == JobEvent.JobAction.CREATED) {
            JobHistory jobHistory = jobHistoryFactory.create(event.getJob());
            jobHistoryRepository.create(jobHistory);
        } else if (event.getAction() == JobEvent.JobAction.UPDATED) {
            Job currentJob = event.getJob();
            Job previousJob = event.getPreviousJob();

            if (!hasSignificantChanges(currentJob, previousJob)) {
                log.info("No significant changes detected in job {}", currentJob.getJobId());
            }

            JobHistory jobHistory = jobHistoryFactory.create(event.getJob());
            jobHistoryRepository.create(jobHistory);
        } else if (event.getAction() == JobEvent.JobAction.DELETED) {
            jobHistoryRepository.deleteAllByJobId(event.getPreviousJob().getJobId());
        }
    }

    /**
     * 중요한 변경사항이 있는지 확인
     * - 이름, 설명, 실행주기 등 중요 속성만 비교
     * - active 상태 변경은 제외
     */
    private boolean hasSignificantChanges(Job currentJob, Job previousJob) {
        // 실제 Job 클래스의 구현에 맞게 비교 로직 작성
        // 예시:
        if (!currentJob.getJobName().equals(previousJob.getJobName())) return true;
        if (!currentJob.getJobDescription().equals(previousJob.getJobDescription())) return true;

        // SshJob인 경우 스크립트 비교
        if (currentJob instanceof SshJob currentSshJob && previousJob instanceof SshJob previousSshJob) {
            // 리스트의 모든 원소 및 순서까지 동일해야 한다.
            if (!currentSshJob.getTargetAgents().equals(previousSshJob.getTargetAgents())) return true;
            if (!currentSshJob.getScript().equals(previousSshJob.getScript())) return true;
            return !currentSshJob.getPeriod().equals(previousSshJob.getPeriod());
        }

        return false;  // 중요한 변경사항 없음
    }
}