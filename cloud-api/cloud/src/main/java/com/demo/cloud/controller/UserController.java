package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.user.AddUserDto;
import com.demo.cloud.dto.user.PasswordChangeDto;
import com.demo.cloud.dto.user.UpdateUserDto;
import com.demo.cloud.dto.user.UpdatedUserDto;
import com.demo.cloud.dto.user.UserViewDto;
import com.demo.cloud.filterParams.UserFilter;
import com.demo.cloud.mapper.UserMapper;
import com.demo.cloud.model.User;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.security.TokenUtil;
import com.demo.cloud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService service;
    private final AuthenticationService authService;
    private final TokenUtil tokenUtil;
    private final UserMapper mapper;

    public UserController(UserService service, AuthenticationService authService, TokenUtil tokenUtil, UserMapper mapper) {
        this.service = service;
        this.authService = authService;
        this.tokenUtil = tokenUtil;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> addUser(@Valid @RequestBody AddUserDto newDto, UriComponentsBuilder uriBuilder) {
        User newUser = mapper.toModel(newDto);
        User added = service.add(newUser, newDto.getRepeatedPassword(), newDto.getRole(), newDto.getOrganization());

        uriBuilder.path("api/users/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<PaginatedResponse<UserViewDto>> getAll(Pageable pageable, UserFilter filter) {
        Page<User> users = service.getAll(pageable, filter.getParams());
        return ResponseEntity.ok(mapper.toDto(users));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<UserViewDto> getById(@PathVariable Long id) {
        User found = service.getById(id);
        UserViewDto foundDto = mapper.toDto(found);
        return ResponseEntity.ok(foundDto);
    }

    @PutMapping("password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeDto passwordDto) {
        service.changePassword(
                passwordDto.oldPassword(),
                passwordDto.newPassword(),
                passwordDto.repeatedPassword()
        );
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdatedUserDto<UserViewDto>> update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto changesDto) {
        User authenticated = authService.getAuthenticated();
        Long authId = authenticated.getId();
        String authUsername = authenticated.getUsername();

        User changes = mapper.toModel(changesDto);
        User updated = service.update(id, changes, changesDto.getRole(), changesDto.getOrganization());

        String jwt = "";
        if (updated.getId().equals(authId) && !updated.getUsername().equals(authUsername)) {
            jwt = tokenUtil.generateToken(updated.getUsername());
        }

        UserViewDto updatedDto = mapper.toDto(updated);
        return ResponseEntity.ok(new UpdatedUserDto<>(updatedDto, jwt));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
