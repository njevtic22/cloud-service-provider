package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.InvalidPasswordException;
import com.demo.cloud.core.error.exceptions.ModelConstraintException;
import com.demo.cloud.core.error.exceptions.MultipleAffectedRowsException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
import com.demo.cloud.repository.specification.EntitySpecification;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.service.OrganizationService;
import com.demo.cloud.service.RoleService;
import com.demo.cloud.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final OrganizationService orgService;
    private final AuthenticationService authService;
    private final PasswordEncoder encoder;
    private final EntitySpecification<User> spec;

    public UserServiceImpl(
            UserRepository repository,
            RoleService roleService,
            OrganizationService orgService,
            AuthenticationService authService,
            PasswordEncoder encoder,
            EntitySpecification<User> spec
    ) {
        this.repository = repository;
        this.roleService = roleService;
        this.orgService = orgService;
        this.authService = authService;
        this.encoder = encoder;
        this.spec = spec;
    }

    @Override
    public User add(User newUser, String repeatedPassword, String roleName, Long organizationId) {
        if (!newUser.getPassword().equals(repeatedPassword)) {     // passwords are not encoded
            throw new InvalidPasswordException("New password and repeated password do not match.");
        }

        validateEmail(newUser.getEmail());
        validateUsername(newUser.getUsername());

        Role foundRole = roleService.getByName(roleName);
        Organization foundOrg = orgService.getById(organizationId);

        User toAdd = new User(
                newUser.getName(),
                newUser.getSurname(),
                newUser.getEmail(),
                newUser.getUsername(),
                encoder.encode(newUser.getPassword()),
                false,
                foundRole,
                foundOrg
        );

        return repository.save(toAdd);
    }

    @Override
    public Page<User> getAll(Pageable pageable, Map<String, String> filter) {
        User requester = authService.getAuthenticated();
        if (requester.isAdmin()) {
            filter.put("organization", requester.getOrganization().getName());
        }

        return repository.findAll(spec.get(filter), pageable);
    }

    @Override
    public User getById(Long id) {
        return repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User getByUsername(String username) {
        return repository.findByUsernameAndArchivedFalse(username)
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

    @Override
    public User update(Long id, User changes, String roleName, Long organizationId) {
        Objects.requireNonNull(changes, "Admin changes must not be null.");

        User existing = getById(id);
        if (!existing.getEmail().equals(changes.getEmail())) {
            validateEmail(changes.getEmail());
        }
        if (!existing.getUsername().equals(changes.getUsername())) {
            validateUsername(changes.getUsername());
        }

        Role changedRole = roleService.getByName(roleName);
        if (existing.isSuperAdmin()) {
            if (organizationId != null) {
                throw new ModelConstraintException("Super admin can not belong to organization");
            }
            if (!changedRole.isSuperAdmin()) {
                throw new ModelConstraintException("Super admin can not change role");
            }
        } else {
            if (organizationId == null) {
                throw new ModelConstraintException("Organization id must not be null");
            }
            if (changedRole.isSuperAdmin()) {
                throw new ModelConstraintException("Can not change role to super admin");
            }
        }

        Organization changedOrg = existing.isSuperAdmin() ? null : orgService.getById(organizationId);

        User updated = new User(
                existing.getId(),
                changes.getName(),
                changes.getSurname(),
                changes.getEmail(),
                changes.getUsername(),
                existing.getPassword(),
                existing.isArchived(),
                changedRole,
                changedOrg
        );
        return repository.save(updated);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword, String repeatedPassword) {
        User existing = authService.getAuthenticated();
        validatePasswordMatch(existing.getPassword(), oldPassword, newPassword, repeatedPassword);

        int rowsAffected = repository.updatePasswordById(existing.getId(), encoder.encode(newPassword));
        if (rowsAffected != 1) {
            throw new MultipleAffectedRowsException("Users", "update password (by id)");
        }
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "Id must not be null.");

        if (!repository.existsByIdAndArchivedFalse(id)) {
            throw new EntityNotFoundException("User", id);
        }

        int rowsAffected = repository.archiveById(id);
        if (rowsAffected != 1) {
            throw new MultipleAffectedRowsException("Users", "delete (by id)");
        }
    }

    private void validateEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new UniquePropertyException("Email", email);
        }
    }

    private void validateUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UniquePropertyException("Username", username);
        }
    }

    private void validatePasswordMatch(String dbPassword, String oldPassword, String newPassword, String repeatedPassword) {
        if (!encoder.matches(oldPassword, dbPassword)) {
            throw new InvalidPasswordException("Incorrect password.");
        }

        if (!newPassword.equals(repeatedPassword)) {
            throw new InvalidPasswordException("New password and repeated password do not match.");
        }
    }
}
