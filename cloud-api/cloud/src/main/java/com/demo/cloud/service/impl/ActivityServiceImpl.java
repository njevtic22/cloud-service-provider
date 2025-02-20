package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Activity;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.repository.ActivityRepository;
import com.demo.cloud.service.ActivityService;
import com.demo.cloud.service.VirtualMachineService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static com.demo.cloud.repository.specification.ActivitySpecification.getSpec;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository repository;
    private final VirtualMachineService service;

    public ActivityServiceImpl(ActivityRepository repository, VirtualMachineService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    @Transactional
    public Activity add(Long machineId) {
        VirtualMachine found = service.getById(machineId);

        if (found.isActive()) {
            throw new IllegalArgumentException("Chosen machine is already active");
        }

        Activity toAdd = new Activity(
                LocalDateTime.now(),
                null,
                found,
                0
        );
        Activity saved = repository.save(toAdd);
        found.setActive(true);
        return saved;
    }

    @Override
    public Page<Activity> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(getSpec(filter), pageable);
    }

    @Override
    public Activity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity", id));
    }
}
