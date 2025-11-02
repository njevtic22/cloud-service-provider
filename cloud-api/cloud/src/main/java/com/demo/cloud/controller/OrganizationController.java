package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.image.ImageViewDto;
import com.demo.cloud.dto.organization.AddOrganizationDto;
import com.demo.cloud.dto.organization.OrganizationViewDto;
import com.demo.cloud.dto.organization.UpdateOrganizationDto;
import com.demo.cloud.filterParams.OrganizationFilter;
import com.demo.cloud.mapper.OrganizationMapper;
import com.demo.cloud.model.Organization;
import com.demo.cloud.service.ImageService;
import com.demo.cloud.service.OrganizationService;
import com.demo.cloud.util.Pair;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("{id}/image")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<OrganizationViewDto> uploadImage(@PathVariable Long id, @RequestPart("image") MultipartFile image) throws IOException {
        Organization found = service.getById(id);
        String logo = found.getLogo();

        byte[] content = image.getBytes();
        String type = image.getContentType();

        String uploadedType = type.split("/")[1];
        String uploaded = imgService.upload(content, "organization " + found.getId(), uploadedType);

        service.updateLogo(found.getId(), uploaded);

        deleteOldLogo(logo, uploadedType);

        Pair<byte[], String> read = imgService.read(uploaded);
        OrganizationViewDto foundDto = mapper.toDto(found, new ImageViewDto(read.first(), read.second()));
        return ResponseEntity.ok(foundDto);
    }

    @DeleteMapping("{id}/image")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) throws IOException {
        String oldLogo = service.deleteLogo(id);
        imgService.delete(oldLogo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<PaginatedResponse<OrganizationViewDto>> getAll(
            @PageableDefault(sort="id") Pageable pageable,
            OrganizationFilter filter
    ) throws IOException {
        Page<Organization> orgs = service.getAll(pageable, filter.getParams());

        List<OrganizationViewDto> orgsDto = new ArrayList<>(orgs.getNumberOfElements());
        for (Organization org : orgs.getContent()) {
            Pair<byte[], String> image = imgService.read(org.getLogo());
            ImageViewDto imageDto = image == null ? null : new ImageViewDto(image.first(), image.second());
            orgsDto.add(mapper.toDto(org, imageDto));
        }

        return ResponseEntity.ok(new PaginatedResponse<>(
                orgs.getTotalElements(),
                orgs.getTotalPages(),
                orgsDto
        ));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<OrganizationViewDto> getById(@PathVariable Long id) throws IOException {
        Organization found = service.getById(id);
        Pair<byte[], String> image = imgService.read(found.getLogo());
        OrganizationViewDto foundDto = mapper.toDto(found, new ImageViewDto(image.first(), image.second()));
        return ResponseEntity.ok(foundDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<OrganizationViewDto> update(@PathVariable Long id, @Valid @RequestBody UpdateOrganizationDto changesDto) throws IOException {
        Organization changes = mapper.toModel(changesDto);
        Organization updated = service.update(id, changes);
        Pair<byte[], String> read = imgService.read(updated.getLogo());
        OrganizationViewDto updatedDto = mapper.toDto(updated, new ImageViewDto(read.first(), read.second()));
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void deleteOldLogo(String oldLogo, String newType) throws IOException {
        if (oldLogo == null) {
            return;
        }

        String[] logoSplit = oldLogo.split("\\.");
        String logoType = logoSplit[logoSplit.length - 1];
        if (!logoType.equals(newType)) {
            imgService.delete(oldLogo);
        }
    }
}
