package com.demo.cloud.dto.user;

import com.demo.cloud.core.validation.annotation.Password;
import jakarta.validation.constraints.NotBlank;

public class AddUserDto extends RequestUserDto {
    @Password
    private final String password;

    @NotBlank(message = "Repeated password must not be blank.")
    private final String repeatedPassword;

    public AddUserDto(String name, String surname, String email, String username, Long organization, String role, String password, String repeatedPassword) {
        super(name, surname, email, username, organization, role);
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }


    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }
}
