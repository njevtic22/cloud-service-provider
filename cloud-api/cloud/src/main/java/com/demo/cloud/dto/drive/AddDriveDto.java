package com.demo.cloud.dto.drive;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddDriveDto extends RequestDriveDto {
    @NotNull(message = "Organization id must not be null.")
    @Positive(message = "Organization id must be positive int.")
    private final Long orgId;

    public AddDriveDto(String name, int capacity, String type, Long orgId, Long orgId1) {
        super(name, capacity, type);
        this.orgId = orgId1;
    }

    public Long getOrgId() {
        return orgId;
    }
}
