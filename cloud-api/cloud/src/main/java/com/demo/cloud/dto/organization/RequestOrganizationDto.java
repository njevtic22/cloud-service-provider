package com.demo.cloud.dto.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public abstract class RequestOrganizationDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @Size(max = 1000, message = "Description must be at most 1000 characters long")
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
