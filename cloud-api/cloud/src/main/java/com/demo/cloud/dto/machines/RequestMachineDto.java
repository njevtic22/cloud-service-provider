package com.demo.cloud.dto.machines;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public abstract class RequestMachineDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @Positive(message = "Organization id must be positive long.")
    private final Long organizationId;

    @NotNull(message = "Category id must not be null.")
    @Positive(message = "Category id must be positive long.")
    private final Long categoryId;

    protected RequestMachineDto(String name, Long organizationId, Long categoryId) {
        this.name = name;
        this.organizationId = organizationId;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
