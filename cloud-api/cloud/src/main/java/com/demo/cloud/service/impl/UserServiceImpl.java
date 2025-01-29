package com.demo.cloud.service.impl;

import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
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

    public UserServiceImpl(UserRepository repository, RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    @Override
    public User add(User newUser, String repeatedPassword, String roleName, Long organizationId) {
        Role byName = roleService.getByName(roleName);
        return null;
    }

    @Override
    public Page<User> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(getSpec(filter), pageable);
    }
}
