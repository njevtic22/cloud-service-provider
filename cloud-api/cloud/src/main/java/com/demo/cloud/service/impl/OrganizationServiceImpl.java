package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Organization;
import com.demo.cloud.repository.OrganizationRepository;
import com.demo.cloud.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static com.demo.cloud.repository.specification.OrganizationSpecification.getSpec;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;

    public OrganizationServiceImpl(OrganizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Organization add(Organization newOrg) {
        validateName(newOrg.getName());
        validateDescription(newOrg.getDescription());

        Organization toAdd = new Organization(
                newOrg.getName(),
                newOrg.getDescription(),
                null,
                false
        );
        return repository.save(toAdd);
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

    @Override
    @Transactional
    public void updateLogo(Long id, String logo) {
        int rowsAffected = repository.updateLogo(id, logo);
        if (rowsAffected != 1) {
            throw new RuntimeException("Zero or more than one rows in organizations table is affected by updateLogo operation.");
        }
    }

    @Override
    public Organization update(Long id, Organization changes) {
        Objects.requireNonNull(changes, "Organization changes must not be null.");

        Organization existing = getById(id);
        if (!existing.getName().equals(changes.getName())) {
            validateName(changes.getName());
        }
        validateDescription(changes.getDescription());

        Organization updated = new Organization(
                existing.getId(),
                changes.getName(),
                changes.getDescription(),
                existing.getLogo(),
                existing.isArchived()
        );
        return repository.save(updated);
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name '" + name + "' is already taken");
        }
    }

    private void validateDescription(String description) {
        if (description.length() > 1000) {
            throw new IllegalArgumentException("Description must be at most 1000 characters long");
        }
    }
}
