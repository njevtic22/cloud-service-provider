package com.demo.cloud.dto.category;

public class UpdateCategoryDto extends AddCategoryDto{
    public UpdateCategoryDto(String name, int cpu, int ram, int gpu) {
        super(name, cpu, ram, gpu);
    }
}
