package com.demo.cloud.dto.organization;

public record OrganizationViewDto(
        Long id,
        String name,
        String description,
        // TODO: Change to image
        String logo
) {
}
