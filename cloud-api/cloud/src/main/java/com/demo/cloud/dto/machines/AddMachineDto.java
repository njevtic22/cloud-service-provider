package com.demo.cloud.dto.machines;

import jakarta.validation.constraints.Positive;

public class AddMachineDto extends RequestMachineDto {
    @Positive(message = "Organization id must be positive long.")
    private final Long organizationId;

    public AddMachineDto(String name, Long categoryId, Long organizationId) {
        super(name, categoryId);
        this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }
}
