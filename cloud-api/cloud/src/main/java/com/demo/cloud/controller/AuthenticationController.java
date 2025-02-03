package com.demo.cloud.controller;

import com.demo.cloud.dto.auth.LoginDto;
import com.demo.cloud.dto.auth.LoginRequestDto;
import com.demo.cloud.security.AuthenticationService;
import com.demo.cloud.util.Pair;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        Pair<String, String> loginData = service.authenticate(loginRequest.username(), loginRequest.password());
        return ResponseEntity.ok(new LoginDto(loginData.first(), loginData.second()));
    }
}
