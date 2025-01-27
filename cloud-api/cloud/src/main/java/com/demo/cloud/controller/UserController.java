package com.demo.cloud.controller;

import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.filterParams.UserFilter;
import com.demo.cloud.model.User;
import com.demo.cloud.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<User>> getAll(Pageable pageable, UserFilter filter) {
        Page<User> users = service.getAll(pageable, filter.getParams());
        return ResponseEntity.ok(new PaginatedResponse<>(
                users.getContent(),
                users.getTotalElements(),
                users.getTotalPages()
        ));
    }
}
