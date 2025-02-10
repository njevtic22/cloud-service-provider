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
        return null;
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
