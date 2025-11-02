package com.demo.cloud.dto.organization;

import com.demo.cloud.dto.image.ImageViewDto;

public class OrganizationViewDto {
    private final Long id;
    private final String name;
    private final String description;
    private final ImageViewDto logo;

    public OrganizationViewDto(
            Long id,
            String name,
            String description,
            ImageViewDto logo
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ImageViewDto getLogo() {
        return logo;
    }
}
