package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Category;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.User;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.repository.VirtualMachineRepository;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.service.OrganizationService;
import com.demo.cloud.service.VirtualMachineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.cloud.repository.specification.MachineSpecification.getSpec;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {
    private final VirtualMachineRepository repository;
    private final AuthenticationService authService;
    private final OrganizationService orgService;

    public VirtualMachineServiceImpl(VirtualMachineRepository repository, AuthenticationService authService, OrganizationService orgService) {
        this.repository = repository;
        this.authService = authService;
        this.orgService = orgService;
    }

    @Override
    public VirtualMachine add(String name, Long organizationId, Long categoryId) {
        validateName(name);

        User authenticated = authService.getAuthenticated();
        if (authenticated.isAdmin()) {
            organizationId = authenticated.getOrganization().getId();
        }

        if (organizationId == null) {
            throw new IllegalArgumentException("Organization id must not be null.");
        }

        Organization org = orgService.getById(organizationId);

        // TODO: Update to use categoryId
        Category cat = new Category(
                1L,
                "1: Gap of Rohan",
                1,
                2,
                0,
                false
        );

        VirtualMachine toAdd = new VirtualMachine(
                name,
                org,
                cat,
                false
        );
        return repository.save(toAdd);
    }

    @Override
    public Page<VirtualMachine> getAll(Pageable pageable, Map<String, String> filter) {
        User authenticated = authService.getAuthenticated();
        String orgId = authenticated.getOrganization() == null ? null : authenticated.getOrganization().getId().toString();
        filter.put("organizationId", orgId);

        return repository.findAll(getSpec(filter, false), pageable);
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
        if (!existing.getName().equals(name)) {
            validateName(name);
        }

        // TODO: Update to use categoryId
        Category cat = new Category(
                1L,
                "1: Gap of Rohan",
                1,
                2,
                0,
                false
        );

        VirtualMachine updated = new VirtualMachine(
                existing.getId(),
                name,
                false,
                existing.getOrganization(),
                cat
        );
        return repository.save(updated);
    }

    private void validateName(String name) {
        if (repository.existsByName(name)) {
            throw new UniquePropertyException("Name '" + name + "' is already taken");
        }
    }
}
