package com.demo.cloud.mapper;

import com.demo.cloud.dto.user.AddUserDto;
import com.demo.cloud.dto.user.UserViewDto;
import com.demo.cloud.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserViewDto toViewDto(User user) {
        return new UserViewDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName()
        );
    }

    public User toModel(AddUserDto dto) {
        return new User(
                dto.getName(),
                dto.getSurname(),
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword(),
                null,
                false,
                null
        );
    }
}
