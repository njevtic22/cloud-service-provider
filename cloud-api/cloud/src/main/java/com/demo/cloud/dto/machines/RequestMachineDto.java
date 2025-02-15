package com.demo.cloud.dto.machines;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public abstract class RequestMachineDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @NotNull(message = "Category id must not be null.")
    @Positive(message = "Category id must be positive long.")
    private final Long categoryId;

    protected RequestMachineDto(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }
    public Long getCategoryId() {
        return categoryId;
    }
}
