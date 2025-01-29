package com.demo.cloud.dto.user;

public record UserViewDto(
        Long id,
        String name,
        String surname,
        String email,
        String username,
        String role,
        Long organization
) {
}
