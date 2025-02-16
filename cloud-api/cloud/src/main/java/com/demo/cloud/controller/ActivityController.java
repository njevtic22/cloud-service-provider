package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.activity.ActivityViewDto;
import com.demo.cloud.mapper.ActivityMapper;
import com.demo.cloud.model.Activity;
import com.demo.cloud.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/activities")
public class ActivityController {
    private final ActivityService service;
    private final ActivityMapper mapper;

    public ActivityController(ActivityService service, ActivityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ActivityViewDto>> getAll(Pageable pageable) {
        Page<Activity> activities = service.getAll(pageable, null);
        List<ActivityViewDto> activitiesDto = activities.getContent()
                .stream()
                .map(mapper::toViewDto)
                .toList();

        return ResponseEntity.ok(new PaginatedResponse<>(
                activitiesDto,
                activities.getTotalElements(),
                activities.getTotalPages()
        ));
    }
}
