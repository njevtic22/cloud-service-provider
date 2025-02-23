package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.drive.DriveViewDto;
import com.demo.cloud.filterParams.DriveFilter;
import com.demo.cloud.mapper.DriveMapper;
import com.demo.cloud.model.Drive;
import com.demo.cloud.service.DriveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/drives")
public class DriveController {
    private final DriveService service;
    private final DriveMapper mapper;

    public DriveController(DriveService service, DriveMapper mapper) {
        this.service = service;
        this.mapper = mapper;
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
