package com.demo.cloud.dto.user;

public class UpdateUserDto extends RequestUserDto {
    public UpdateUserDto(String name, String surname, String email, String username, Long organization, String role) {
        super(name, surname, email, username, organization, role);
    }
}
