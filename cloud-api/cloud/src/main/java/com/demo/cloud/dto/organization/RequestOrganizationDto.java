package com.demo.cloud.dto.organization;

import jakarta.validation.constraints.NotBlank;

public abstract class RequestOrganizationDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    // TODO: @Size
    @NotBlank(message = "Description must not be blank.")
    private final String description;

    protected RequestOrganizationDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
