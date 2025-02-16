package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Activity;
import com.demo.cloud.repository.ActivityRepository;
import com.demo.cloud.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository repository;

    public ActivityServiceImpl(ActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Activity> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(pageable);
    }

    @Override
    public Activity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity", id));
    }
}
