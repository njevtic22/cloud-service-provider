package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
import com.demo.cloud.service.OrganizationService;
import com.demo.cloud.service.RoleService;
import com.demo.cloud.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.cloud.repository.specification.UserSpecification.getSpec;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final OrganizationService orgService;

    public UserServiceImpl(UserRepository repository, RoleService roleService, OrganizationService orgService) {
        this.repository = repository;
        this.roleService = roleService;
        this.orgService = orgService;
    }

    @Override
    public User add(User newUser, String repeatedPassword, String roleName, Long organizationId) {
        Role byName = roleService.getByName(roleName);
        Organization byId = orgService.getById(organizationId);

        return null;
    }

    @Override
    public Page<User> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(getSpec(filter), pageable);
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }
}
