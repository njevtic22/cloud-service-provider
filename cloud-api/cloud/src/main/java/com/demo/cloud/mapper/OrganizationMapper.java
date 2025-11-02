package com.demo.cloud.mapper;

import com.demo.cloud.dto.image.ImageViewDto;
import com.demo.cloud.dto.organization.AddOrganizationDto;
import com.demo.cloud.dto.organization.OrganizationViewDto;
import com.demo.cloud.dto.organization.SingleOrganizationDto;
import com.demo.cloud.dto.organization.UpdateOrganizationDto;
import com.demo.cloud.model.Organization;
import com.demo.cloud.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationViewDto toDto(Organization org, Pair<byte[], String> image) {
        ImageViewDto imageDto = image == null ? null : new ImageViewDto(image.first(), image.second());
        return new OrganizationViewDto(
                org.getId(),
                org.getName(),
                org.getDescription(),
                imageDto
        );
    }

    public SingleOrganizationDto toDto(Organization org, Pair<byte[], String> image, long users, long machines, long drivers) {
        ImageViewDto imageDto = image == null ? null : new ImageViewDto(image.first(), image.second());
        return new SingleOrganizationDto(
                org.getId(),
                org.getName(),
                org.getDescription(),
                imageDto,
                users,
                machines,
                drivers
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
