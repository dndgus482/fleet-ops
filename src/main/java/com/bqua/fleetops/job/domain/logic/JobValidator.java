package com.bqua.fleetops.job.domain.logic;

import com.bqua.fleetops.common.exception.ValidationException;
import com.bqua.fleetops.common.type.SchedulePeriod;
import com.bqua.fleetops.job.domain.entity.job.Job;
import com.bqua.fleetops.job.domain.entity.job.SshJob;
import com.bqua.fleetops.job.domain.entity.job.SshScript;
import com.bqua.fleetops.job.domain.entity.jobexecution.TargetAgent;
import com.bqua.fleetops.job.domain.outbound.SshJobCommandsPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bqua.fleetops.common.validator.ValidationUtil.shouldNotNull;
import static com.bqua.fleetops.common.validator.ValidationUtil.shouldNotNullOrEmpty;

/**
 * DTO 에서 @NotNull, @Size, @NotEmpty, @NotBlank 정도의 1차 검사는 진행하기 때문에
 * 여기서는 그런 검사는 생략한다. 하지만 필요하면 추가될 수 있다.
 * 다만 List(String) 같이 커스텀 하지 않은 타입(String 등)을 사용하는 제네릭 타입은 안의 내용이 empty 인지 등.. 검사를 안할 수 있기 때문에 검사해줘야 한다.
 */
@Service
@RequiredArgsConstructor
public class JobValidator {
    private final SshJobCommandsPort sshJobCommandsPort;

    public void validate(Job job) {
        if (StringUtils.isBlank(job.getJobId())) {
            throw new ValidationException("Job ID must not be blank.");
        }
        if (StringUtils.isBlank(job.getJobName())) {
            throw new ValidationException("Job name must not be blank.");
        }
    }

    public void validate(SshJob sshJob) {
        validate((Job) sshJob);
        validateSshScript(sshJob.getScript());
        validateSchedulePeriod(sshJob.getPeriod());
        validateTargetAgents(sshJob.getTargetAgents());
    }

    public void validateSshScript(SshScript script) {
        if (script == null || StringUtils.isBlank(script.getValue())) {
            throw new ValidationException("스크립트 내용이 비어 있습니다.");
        }

        String validationResult = sshJobCommandsPort.checkScriptSyntaxLocally(script.getValue());

        if (!validationResult.isEmpty()) {
            throw new ValidationException("스크립트 문법 오류: " + validationResult);
        }
    }

    public void validateSchedulePeriod(SchedulePeriod period) {
        shouldNotNull(period, "period");
        period.validate();
    }

    public void validateTargetAgents(List<TargetAgent> targetAgents) {
        shouldNotNullOrEmpty(targetAgents, "target agents");
    }









}
