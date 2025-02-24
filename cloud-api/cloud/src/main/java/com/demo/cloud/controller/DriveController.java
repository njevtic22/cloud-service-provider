package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.drive.AddDriveDto;
import com.demo.cloud.dto.drive.DriveViewDto;
import com.demo.cloud.filterParams.DriveFilter;
import com.demo.cloud.mapper.DriveMapper;
import com.demo.cloud.model.Drive;
import com.demo.cloud.service.DriveService;
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

import java.net.URI;

@RestController
@RequestMapping("api/drives")
public class DriveController {
    private final DriveService service;
    private final DriveMapper mapper;

    public DriveController(DriveService service, DriveMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> add(@Valid @RequestBody AddDriveDto newDto, UriComponentsBuilder uriBuilder) {
        Drive newDrive = mapper.toModel(newDto);
        Drive added = service.add(newDrive, newDto.getOrgId());

        uriBuilder.path("api/drives/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping
    public ResponseEntity<PaginatedResponse<DriveViewDto>> getAll(Pageable pageable, DriveFilter filter) {
        Page<Drive> drives = service.getAll(pageable, filter.getParams());
        return ResponseEntity.ok(mapper.toDto(drives));
    }

    @GetMapping("{id}")
    public ResponseEntity<DriveViewDto> getById(@PathVariable Long id) {
        Drive found = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(found));
    }
}
