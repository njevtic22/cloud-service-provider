package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Drive;
import com.demo.cloud.repository.DriveRepository;
import com.demo.cloud.service.DriveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.cloud.repository.specification.DriveSpecification.getSpec;

@Service
public class DriveServiceImpl implements DriveService {
    private final DriveRepository repository;

    public DriveServiceImpl(DriveRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Drive> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(getSpec(filter, false), pageable);
    }

    @Override
    public Drive getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Drive", id));
    }
}
