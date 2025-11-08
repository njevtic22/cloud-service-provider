package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.ModelConstraintException;
import com.demo.cloud.core.error.exceptions.MultipleAffectedRowsException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Category;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.User;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.repository.VirtualMachineRepository;
import com.demo.cloud.repository.specification.EntitySpecification;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.service.CategoryService;
import com.demo.cloud.service.OrganizationService;
import com.demo.cloud.service.VirtualMachineService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {
    private final VirtualMachineRepository repository;
    private final AuthenticationService authService;
    private final OrganizationService orgService;
    private final CategoryService catService;
    private final EntitySpecification<VirtualMachine> spec;

    public VirtualMachineServiceImpl(
            VirtualMachineRepository repository,
            AuthenticationService authService,
            OrganizationService orgService,
            CategoryService catService,
            EntitySpecification<VirtualMachine> spec
    ) {
        this.repository = repository;
        this.authService = authService;
        this.orgService = orgService;
        this.catService = catService;
        this.spec = spec;
    }

    @Override
    public VirtualMachine add(String name, Long organizationId, Long categoryId) {
        validateName(name);

        User authenticated = authService.getAuthenticated();
        if (authenticated.isAdmin()) {
            organizationId = authenticated.getOrganization().getId();
        }

        if (organizationId == null) {
            throw new ModelConstraintException("Organization id must not be null.");
        }

        Organization org = orgService.getById(organizationId);
        Category cat = catService.getById(categoryId);

        VirtualMachine toAdd = new VirtualMachine(
                name,
                false,
                false,
                org,
                cat
        );
        return repository.save(toAdd);
    }

    @Override
    public Page<VirtualMachine> getAll(Pageable pageable, Map<String, String> filter) {
        User authenticated = authService.getAuthenticated();
        String orgId = authenticated.getOrganization() == null ? null : authenticated.getOrganization().getId().toString();
        filter.put("organizationId", orgId);

        return repository.findAll(spec.get(filter), pageable);
    }

    @Override
    public VirtualMachine getById(Long id) {
        User authenticated = authService.getAuthenticated();

        VirtualMachine machine;
        if (authenticated.isSuperAdmin()) {
            machine = repository.findByIdAndArchivedFalse(id)
                    .orElseThrow(() -> new EntityNotFoundException("Virtual machine", id));
        } else {
            Long orgId = authenticated.getOrganization().getId();
            machine = repository.findByIdAndOrganizationIdAndArchivedFalse(id, orgId)
                    .orElseThrow(() -> new EntityNotFoundException("Virtual machine", id));
        }

        return machine;
    }

    @Override
    public VirtualMachine update(Long id, String name, Long categoryId) {
        VirtualMachine existing = getById(id);

        // TODO: Prevent update if machine is active

        if (!existing.getName().equals(name)) {
            validateName(name);
        }

        Category cat = catService.getById(categoryId);
        VirtualMachine updated = new VirtualMachine(
                existing.getId(),
                name,
                existing.isActive(),
                false,
                existing.getOrganization(),
                cat
        );
        return repository.save(updated);
    }

    @Override
    public long count(Map<String, String> filter) {
        return repository.count(spec.get(filter));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Id must not be null.");

        // TODO: Validate if admin is deleting machine which belongs to his organization

        if (!repository.existsByIdAndArchivedFalse(id)) {
            throw new EntityNotFoundException("Virtual machine", id);
        }

        // TODO: do not delete if machine is active

        int rowsAffected = repository.archiveById(id);
        if (rowsAffected != 1) {
            throw new MultipleAffectedRowsException("Virtual machines", "delete (by id)");
        }
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name", name);
        }
    }
}
