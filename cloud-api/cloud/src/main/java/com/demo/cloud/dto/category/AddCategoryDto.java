package com.demo.cloud.dto.category;

public class AddCategoryDto extends RequestCategoryDto {
    public AddCategoryDto(String name, int cpu, int ram, int gpu) {
        super(name, cpu, ram, gpu);
    }
}
