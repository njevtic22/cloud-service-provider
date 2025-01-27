package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.dto.user.UserViewDto;
import com.demo.cloud.filterParams.UserFilter;
import com.demo.cloud.mapper.UserMapper;
import com.demo.cloud.model.User;
import com.demo.cloud.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
