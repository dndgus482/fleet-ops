package com.bqua.fleetops.job.inbound.dto;

import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Response containing details of a job")
public class JobRes {

    @Schema(description = "Unique identifier of the job", example = "job123")
    @NotNull
    private String jobId;

    @Schema(description = "Name of the job", example = "Data Analysis Job")
    @NotNull
    private String jobName;

    @Schema(description = "Description of the job", example = "A job that analyzes input data and produces insights")
    @NotNull
    private String jobDescription;

    @Schema(description = "Type of the job", example = "SSH")
    @NotNull
    private JobType jobType;

    @Schema(description = "Indicates whether the job is active or not", example = "true")
    @NotNull
    private boolean active;

    @Schema(description = "List of target agents assigned to the job")
    @Nullable
    private List<TargetAgentRes> targetAgents;

    @Schema(description = "Execution period of the job in cron format", example = "0 0 * * *")
    @Nullable
    private String period;

    @Schema(description = "Script associated with the job", example = "data_processing_script.sh")
    @Nullable
    private String script;
}