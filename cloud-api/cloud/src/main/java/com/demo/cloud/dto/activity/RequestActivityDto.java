package com.demo.cloud.dto.activity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public abstract class RequestActivityDto {
    @NotNull(message = "Machine id must not be null.")
    @Positive(message = "Machine id must be positive long.")
    private final Long machineId;

    protected RequestActivityDto(Long machineId) {
        this.machineId = machineId;
    }

    public Long getMachineId() {
        return machineId;
    }
}
