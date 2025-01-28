package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.user.AddUserDto;
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
}
