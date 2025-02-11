package com.demo.cloud.dto.machines;

public record MachineViewDto(
        Long id,
        String name,
//        Long organizationId,
        String organization,
//        Long categoryId,
        String category,
        int cpu,
        int ram,
        int gpu
) {
}
