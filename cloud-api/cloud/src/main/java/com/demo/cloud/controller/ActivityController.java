package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.activity.ActivityViewDto;
import com.demo.cloud.dto.activity.AddActivityDto;
import com.demo.cloud.dto.activity.UpdateActivityDto;
import com.demo.cloud.filterParams.ActivityFilter;
import com.demo.cloud.mapper.ActivityMapper;
import com.demo.cloud.model.Activity;
import com.demo.cloud.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/activities")
public class ActivityController {
    private final ActivityService service;
    private final ActivityMapper mapper;

    public ActivityController(ActivityService service, ActivityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> add(@Valid @RequestBody AddActivityDto newDto, UriComponentsBuilder uriBuilder) {
        Activity added = service.add(newDto.getMachineId());

        uriBuilder.path("api/activities/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ActivityViewDto>> getAll(
            @PageableDefault(sort="id") Pageable pageable, 
            ActivityFilter filter
    ) {
        Page<Activity> activities = service.getAll(pageable, filter.getParams());
        return ResponseEntity.ok(mapper.toDto(activities));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<ActivityViewDto> end(@Valid @RequestBody UpdateActivityDto updateDto) {
        Activity ended = service.end(updateDto.getMachineId());
        return ResponseEntity.ok(mapper.toDto(ended));
    }
}
