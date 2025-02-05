package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.organization.OrganizationViewDto;
import com.demo.cloud.filterParams.OrganizationFilter;
import com.demo.cloud.mapper.OrganizationMapper;
import com.demo.cloud.model.Organization;
import com.demo.cloud.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/organizations")
public class OrganizationController {
    private final OrganizationService service;
    private final OrganizationMapper mapper;

    public OrganizationController(OrganizationService service, OrganizationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<PaginatedResponse<OrganizationViewDto>> getAll(Pageable pageable, OrganizationFilter filter) {
        Page<Organization> orgs = service.getAll(pageable, filter.getParams());
        List<OrganizationViewDto> orgsDto = orgs.getContent()
                .stream()
                .map(mapper::toViewDto)
                .toList();

        return ResponseEntity.ok(new PaginatedResponse<>(
                orgsDto,
                orgs.getTotalElements(),
                orgs.getTotalPages()
        ));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<OrganizationViewDto> getById(@PathVariable Long id) {
        Organization found = service.getById(id);
        OrganizationViewDto foundDto = mapper.toViewDto(found);
        return ResponseEntity.ok(foundDto);
    }
}
