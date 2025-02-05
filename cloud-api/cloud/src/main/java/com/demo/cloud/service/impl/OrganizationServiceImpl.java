package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Organization;
import com.demo.cloud.repository.OrganizationRepository;
import com.demo.cloud.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.cloud.repository.specification.OrganizationSpecification.getSpec;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;

    public OrganizationServiceImpl(OrganizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Organization> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(getSpec(filter, false), pageable);
    }

    @Override
    public Organization getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization", id));
    }
}
