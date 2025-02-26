package com.demo.cloud.dto.drive;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public abstract class RequestDriveDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @Positive(message = "Capacity must be positive int.")
    private final int capacity;

    @NotBlank(message = "Type must not be blank.")
    private final String type;

    protected RequestDriveDto(String name, int capacity, String type) {
        this.name = name;
        this.capacity = capacity;
        this.type = type;
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
}
