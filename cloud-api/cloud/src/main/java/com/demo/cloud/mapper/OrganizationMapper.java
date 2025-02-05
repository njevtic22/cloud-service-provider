package com.demo.cloud.mapper;

import com.demo.cloud.dto.organization.AddOrganizationDto;
import com.demo.cloud.dto.organization.OrganizationViewDto;
import com.demo.cloud.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationViewDto toViewDto(Organization org) {
        return new OrganizationViewDto(
                org.getId(),
                org.getName(),
                org.getDescription(),
                org.getLogo()
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
}
