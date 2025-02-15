package com.demo.cloud.dto.organization;

import com.demo.cloud.dto.image.ImageViewDto;

public record OrganizationViewDto(
        Long id,
        String name,
        String description,
        ImageViewDto logo
) {
}
