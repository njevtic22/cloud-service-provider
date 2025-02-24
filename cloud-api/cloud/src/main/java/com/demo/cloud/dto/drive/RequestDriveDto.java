package com.demo.cloud.dto.drive;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public abstract class RequestDriveDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @Positive(message = "Capacity must be positive int.")
    private final int capacity;

    @NotBlank(message = "Type must not be blank.")
    private final String type;

    @NotNull(message = "Organization id must not be null.")
    @Positive(message = "Organization id must be positive int.")
    private final Long orgId;

    protected RequestDriveDto(String name, int capacity, String type, Long orgId) {
        this.name = name;
        this.capacity = capacity;
        this.type = type;
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    public Long getOrgId() {
        return orgId;
    }
}
