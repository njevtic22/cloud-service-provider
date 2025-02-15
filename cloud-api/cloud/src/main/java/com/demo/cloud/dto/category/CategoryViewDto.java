package com.demo.cloud.dto.category;

public record CategoryViewDto(
        Long id,
        String name,
        int cpu,
        int ram,
        int gpu
) {
}
