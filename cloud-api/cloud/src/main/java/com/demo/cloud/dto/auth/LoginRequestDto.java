package com.demo.cloud.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Username must not be blank.") String username,
        @NotBlank(message = "Password must not be blank.") String password
) {
}
