package com.demo.cloud.dto.user;

public record UpdatedUserDto<T extends UserViewDto>(T updated, String token) {
}
