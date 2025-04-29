package com.bqua.fleetops.job.application;

import com.bqua.fleetops.common.exception.ValidationException;
import com.bqua.fleetops.job.application.mapper.JobDtoMapper;
import com.bqua.fleetops.job.domain.event.JobEvent;
import com.bqua.fleetops.job.domain.outbound.JobEventPublisher;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.logic.JobValidator;
import com.bqua.fleetops.job.domain.outbound.JobEntityRepository;
import com.bqua.fleetops.job.inbound.dto.SaveJobReq;
import com.bqua.fleetops.job.inbound.dto.SwitchActiveReq;
import com.bqua.fleetops.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobCommandService {

    private final JobEntityRepository jobEntityRepository;
    private final JobValidator jobValidator;
    private final JobEventPublisher jobEventPublisher;

    @Transactional
    public String save(SaveJobReq req) {
        Job job = JobDtoMapper.INSTANCE.toDomain(req);
        job.generateId();
        job.validate(jobValidator);

        Job saved = jobEntityRepository.create(job);
        jobEventPublisher.publish(JobEvent.created(saved));

        return saved.getJobId();
    }

    @Transactional
    public String update(String jobId, SaveJobReq req) {
        Job prevJob = jobEntityRepository.findJobByJobId(jobId)
                .orElseThrow(() -> new ValidationException(jobId));

        Job job = JobDtoMapper.INSTANCE.toDomain(req);
        job.setJobId(jobId);
        job.validate(jobValidator);

        Job updated = jobEntityRepository.update(job);
        jobEventPublisher.publish(JobEvent.updated(prevJob, updated));

        return updated.getJobId();
    }

    @Transactional
    public void deleteById(String jobId) {
        Job prevJob = jobEntityRepository.findJobByJobId(jobId)
                .orElseThrow(() -> new NotFoundException(jobId));

        jobEntityRepository.delete(jobId);
        jobEventPublisher.publish(JobEvent.deleted(prevJob));
    }

    @Transactional
    public void switchActive(String jobId, SwitchActiveReq switchActiveReq) {
        Job job = jobEntityRepository.findJobByJobId(jobId)
                .orElseThrow(() -> new ValidationException(jobId));
        job.setActive(switchActiveReq.getActive());

        Job updated = jobEntityRepository.update(job);
        jobEventPublisher.publish(JobEvent.switchActive(updated));
    }
}
