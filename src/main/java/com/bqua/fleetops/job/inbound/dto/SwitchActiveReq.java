package com.bqua.fleetops.job.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Request to switch the active state of a job")
public class SwitchActiveReq {
    @Schema(description = "Indicates whether the job should be active or inactive", example = "true")
    @NotNull
    private Boolean active;
}
