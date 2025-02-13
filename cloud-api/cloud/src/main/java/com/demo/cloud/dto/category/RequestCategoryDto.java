package com.demo.cloud.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public abstract class RequestCategoryDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @Positive(message = "Cpu cores must be positive integer.")
    private final int cpu;

    @Positive(message = "Ram memory must be positive integer.")
    private final int ram;

    @PositiveOrZero(message = "Gpu cores must be positive integer or zero.")
    private final int gpu;

    protected RequestCategoryDto(String name, int cpu, int ram, int gpu) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
    }

    public String getName() {
        return name;
    }

    public int getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getGpu() {
        return gpu;
    }
}
