package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.InvalidPasswordException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
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
        if (!newUser.getPassword().equals(repeatedPassword)) {     // passwords are not encoded
            throw new InvalidPasswordException("New password and repeated password do not match.");
        }

        validateEmail(newUser.getEmail());
        validateUsername(newUser.getUsername());

        Role role = roleService.getByName(roleName);
        Organization org = orgService.getById(organizationId);

        User toAdd = new User(
                newUser.getName(),
                newUser.getSurname(),
                newUser.getEmail(),
                newUser.getUsername(),
                newUser.getPassword(),
                role,
                false,
                org
        );

        return repository.save(toAdd);
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

    private void validateEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new UniquePropertyException("Email '" + email + "' is already taken.");
        }
    }

    private void validateUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UniquePropertyException("Username '" + username + "' is already taken.");
        }
    }
}
