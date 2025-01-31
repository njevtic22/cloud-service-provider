package com.demo.cloud.dto.user;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeDto(
        @NotBlank(message = "Old password must not be blank.") String oldPassword,
        @NotBlank(message = "New password must not be blank.") String newPassword,
        @NotBlank(message = "Repeated password must not be blank.") String repeatedPassword
) {
}
