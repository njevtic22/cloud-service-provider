package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.ModelConstraintException;
import com.demo.cloud.core.error.exceptions.MultipleAffectedRowsException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Organization;
import com.demo.cloud.repository.OrganizationRepository;
import com.demo.cloud.repository.specification.EntitySpecification;
import com.demo.cloud.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;
    private final EntitySpecification<Organization> spec;

    public OrganizationServiceImpl(OrganizationRepository repository, EntitySpecification<Organization> spec) {
        this.repository = repository;
        this.spec = spec;
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
        return repository.findAll(spec.get(filter), pageable);
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
            throw new MultipleAffectedRowsException("Organizations", "update logo");
        }
    }

    @Override
    @Transactional
    public String deleteLogo(Long id) {
        Organization found = getById(id);
        String oldLogo = found.getLogo();
        updateLogo(id, null);
        return oldLogo;
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

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Id must not be null.");

        if (!repository.existsByIdAndArchivedFalse(id)) {
            throw new EntityNotFoundException("Organization", id);
        }

        // TODO: do not delete if there are active machines

        int rowsAffected = repository.archiveById(id);
        if (rowsAffected != 1) {
            throw new MultipleAffectedRowsException("Users", "delete (by id)");
        }
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name", name);
        }
    }

    private void validateDescription(String description) {
        if (description.length() > 1000) {
            throw new ModelConstraintException("Description must be at most 1000 characters long");
        }
    }
}
