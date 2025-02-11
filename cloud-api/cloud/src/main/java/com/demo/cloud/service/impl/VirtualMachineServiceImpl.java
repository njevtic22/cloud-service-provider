package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.User;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.repository.VirtualMachineRepository;
import com.demo.cloud.security.AuthenticationService;
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

    public VirtualMachineServiceImpl(VirtualMachineRepository repository, AuthenticationService authService) {
        this.repository = repository;
        this.authService = authService;
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
}
