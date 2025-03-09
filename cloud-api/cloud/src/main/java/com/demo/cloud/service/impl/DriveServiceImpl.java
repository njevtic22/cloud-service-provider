package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.MultipleAffectedRowsException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Drive;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.User;
import com.demo.cloud.repository.DriveRepository;
import com.demo.cloud.repository.specification.EntitySpecification;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.service.DriveService;
import com.demo.cloud.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class DriveServiceImpl implements DriveService {
    private final DriveRepository repository;
    private final OrganizationService orgService;
    private final AuthenticationService authService;
    private final EntitySpecification<Drive> spec;

    public DriveServiceImpl(DriveRepository repository, OrganizationService orgService, AuthenticationService authService, EntitySpecification<Drive> spec) {
        this.repository = repository;
        this.orgService = orgService;
        this.authService = authService;
        this.spec = spec;
    }

    @Override
    public Drive add(Drive newDrive, Long orgId) {
        validateName(newDrive.getName());

        User authenticated = authService.getAuthenticated();
        if (authenticated.isAdmin()) {
            orgId = authenticated.getOrganization().getId();
        }

        Organization found = orgService.getById(orgId);
        Drive toAdd = new Drive(
                newDrive.getName(),
                newDrive.getCapacity(),
                newDrive.getType(),
                found,
                null,
                false
        );
        return repository.save(toAdd);
    }

    @Override
    public Page<Drive> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(spec.get(filter), pageable);
    }

    @Override
    public Drive getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Drive", id));
    }

    @Override
    public Drive update(Long id, Drive changes) {
        Drive existing = getById(id);

        // TODO: prevent update if machine is active

        User authenticated = authService.getAuthenticated();
        if (authenticated.isAdmin()) {
            if (!authenticated.getOrganization().equals(existing.getOrganization())) {
                throw new IllegalArgumentException("Admin can only update drive which belong to his organization");
            }
        }

        if (!existing.getName().equals(changes.getName())) {
            validateName(changes.getName());
        }

        Drive toUpdate = new Drive(
                existing.getId(),
                changes.getName(),
                changes.getCapacity(),
                changes.getType(),
                existing.isArchived(),
                existing.getOrganization(),
                existing.getMachine()
        );
        return repository.save(toUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Id must not be null.");

        // TODO: do not delete if drive is connected to active machine

        validateExists(id);

        int rowsAffected = repository.archiveById(id);
        if (rowsAffected != 1) {
            throw new MultipleAffectedRowsException("Drives", "delete (by id)");
        }
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name", name);
        }
    }

    private void validateExists(Long id) {
        User authenticated = authService.getAuthenticated();
        boolean exists = false;
        if (authenticated.isAdmin()) {
            Long orgId = authenticated.getOrganization().getId();
            exists = repository.existsByIdAndOrganizationIdAndArchivedFalse(id, orgId);
        } else {
            exists = repository.existsByIdAndArchivedFalse(id);
        }

        if (!exists) {
            throw new EntityNotFoundException("Drive", id);
        }
    }
}
