package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Drive;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.User;
import com.demo.cloud.repository.DriveRepository;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.service.DriveService;
import com.demo.cloud.service.OrganizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.cloud.repository.specification.DriveSpecification.getSpec;

@Service
public class DriveServiceImpl implements DriveService {
    private final DriveRepository repository;
    private final OrganizationService orgService;
    private final AuthenticationService authService;

    public DriveServiceImpl(DriveRepository repository, OrganizationService orgService, AuthenticationService authService) {
        this.repository = repository;
        this.orgService = orgService;
        this.authService = authService;
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
        return repository.findAll(getSpec(filter, false), pageable);
    }

    @Override
    public Drive getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Drive", id));
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name '" + name + "' is already taken");
        }
    }
}
