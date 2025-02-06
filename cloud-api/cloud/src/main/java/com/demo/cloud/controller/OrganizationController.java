package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.image.ImageViewDto;
import com.demo.cloud.dto.organization.AddOrganizationDto;
import com.demo.cloud.dto.organization.OrganizationViewDto;
import com.demo.cloud.filterParams.OrganizationFilter;
import com.demo.cloud.mapper.OrganizationMapper;
import com.demo.cloud.model.Organization;
import com.demo.cloud.service.ImageService;
import com.demo.cloud.service.OrganizationService;
import com.demo.cloud.util.Pair;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/organizations")
public class OrganizationController {
    private final OrganizationService service;
    private final ImageService imgService;
    private final OrganizationMapper mapper;

    public OrganizationController(OrganizationService service, ImageService imgService, OrganizationMapper mapper) {
        this.service = service;
        this.imgService = imgService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> add(@Valid @RequestBody AddOrganizationDto newDto, UriComponentsBuilder uriBuilder) {
        Organization newOrg = mapper.toModel(newDto);
        Organization added = service.add(newOrg);

        uriBuilder.path("api/organizations/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<PaginatedResponse<OrganizationViewDto>> getAll(Pageable pageable, OrganizationFilter filter) throws IOException {
        Page<Organization> orgs = service.getAll(pageable, filter.getParams());

        List<OrganizationViewDto> orgsDto = new ArrayList<>(orgs.getNumberOfElements());
        for (Organization org : orgs.getContent()) {
            Pair<byte[], String> image = imgService.read(org.getLogo());
            orgsDto.add(mapper.toViewDto(org, new ImageViewDto(image.first(), image.second())));
        }

        return ResponseEntity.ok(new PaginatedResponse<>(
                orgsDto,
                orgs.getTotalElements(),
                orgs.getTotalPages()
        ));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<OrganizationViewDto> getById(@PathVariable Long id) throws IOException {
        Organization found = service.getById(id);
        Pair<byte[], String> image = imgService.read(found.getLogo());
        OrganizationViewDto foundDto = mapper.toViewDto(found, new ImageViewDto(image.first(), image.second()));
        return ResponseEntity.ok(foundDto);
    }
}
