package com.demo.cloud.dto.machine;

public record MachineViewDto(
        Long id,
        String name,
        boolean active,
//        Long organizationId,
        String organization,
//        Long categoryId,
        String category,
        int cpu,
        int ram,
        int gpu
) {
}
