package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.user.AddUserDto;
import com.demo.cloud.dto.user.UpdateUserDto;
import com.demo.cloud.dto.user.UserViewDto;
import com.demo.cloud.filterParams.UserFilter;
import com.demo.cloud.mapper.UserMapper;
import com.demo.cloud.model.User;
import com.demo.cloud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@Valid @RequestBody AddUserDto newDto, UriComponentsBuilder uriBuilder) {
        User newUser = mapper.toModel(newDto);
        User added = service.add(newUser, newDto.getRepeatedPassword(), newDto.getRole(), newDto.getOrganization());

        uriBuilder.path("api/users/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<UserViewDto>> getAll(Pageable pageable, UserFilter filter) {
        Page<User> users = service.getAll(pageable, filter.getParams());
        List<UserViewDto> userDto = users.getContent()
                .stream()
                .map(mapper::toViewDto)
                .toList();

        return ResponseEntity.ok(new PaginatedResponse<>(
                userDto,
                users.getTotalElements(),
                users.getTotalPages()
        ));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserViewDto> getById(@PathVariable Long id) {
        User found = service.getById(id);
        UserViewDto foundDto = mapper.toViewDto(found);
        return ResponseEntity.ok(foundDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserViewDto> update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto changesDto) {
        User changes = mapper.toModel(changesDto);
        User updated = service.update(id, changes, changesDto.getRole(), changesDto.getOrganization());
        UserViewDto updatedDto = mapper.toViewDto(updated);
        return ResponseEntity.ok(updatedDto);
    }
}
