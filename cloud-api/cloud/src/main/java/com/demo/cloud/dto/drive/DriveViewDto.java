package com.demo.cloud.dto.drive;

public record DriveViewDto(
        Long id,
        String name,
        int capacity,
        String type,
//        Long organizationId,
        String organization,
//        Long machineId,
        String machine
) {
}
