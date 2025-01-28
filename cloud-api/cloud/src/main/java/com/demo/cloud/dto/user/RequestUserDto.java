package com.demo.cloud.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RequestUserDto {
    @NotBlank(message = "Name must not be blank.")
    private final String name;

    @NotBlank(message = "Surname must not be blank.")
    private final String surname;

    @NotBlank(message = "Email must not be blank.")
    private final String email;

    @NotBlank(message = "Username must not be blank.")
    private final String username;

    @NotNull(message = "Organization must not be null.")
    @Positive(message = "Organization must be positive long.")
    private final Long organization;

    @NotBlank(message = "Role must not be blank.")
    private final String role;

    public RequestUserDto(String name, String surname, String email, String username, Long organization, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.organization = organization;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Long getOrganization() {
        return organization;
    }

    public String getRole() {
        return role;
    }
}
