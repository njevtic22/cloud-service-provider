package com.demo.cloud.mapper;

import com.demo.cloud.dto.image.ImageViewDto;
import com.demo.cloud.dto.organization.AddOrganizationDto;
import com.demo.cloud.dto.organization.OrganizationViewDto;
import com.demo.cloud.dto.organization.UpdateOrganizationDto;
import com.demo.cloud.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationViewDto toDto(Organization org, ImageViewDto logo) {
        return new OrganizationViewDto(
                org.getId(),
                org.getName(),
                org.getDescription(),
                logo
        );
    }

    public Organization toModel(AddOrganizationDto dto) {
        return new Organization(
                dto.getName(),
                dto.getDescription(),
                null,
                false
        );
    }

    public Organization toModel(UpdateOrganizationDto dto) {
        return new Organization(
                dto.getName(),
                dto.getDescription(),
                null,
                false
        );
    }
}
