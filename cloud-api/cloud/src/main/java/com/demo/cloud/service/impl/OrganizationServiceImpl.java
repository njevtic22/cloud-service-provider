package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Organization;
import com.demo.cloud.repository.OrganizationRepository;
import com.demo.cloud.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;

    public OrganizationServiceImpl(OrganizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Organization> getAll(Pageable pageable, Map<String, String> filter) {
        return null;
    }

    @Override
    public Organization getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization", id));
    }
}
