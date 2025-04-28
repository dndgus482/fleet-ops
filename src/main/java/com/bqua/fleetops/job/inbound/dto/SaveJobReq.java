package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveJobReq {

    @Schema(description = "Name of the job", example = "Data Backup Job")
    @NotBlank(message = "jobName must not be blank")
    private String jobName;

    @Schema(description = "Description of the job", example = "Performs daily backup of data")
    private String jobDescription;

    @Schema(description = "Type of the job", example = "SSH")
    @NotNull(message = "jobType must not be null")
    private JobType jobType;

    @Schema(description = "Indicates whether the job is active or not", example = "true")
    private Boolean active;

    @Nullable
    @Size(min = 1, message = "targetAgents must not be empty")
    @Valid
    private List<TargetAgentReq> targetAgents;

    @Nullable
    @Size(min = 1, max = 100, message = "targetAgents must not be empty")
    private String period;

    @Nullable
    private String script;
}