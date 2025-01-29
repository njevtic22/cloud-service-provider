package com.demo.cloud.mapper;

import com.demo.cloud.dto.user.AddUserDto;
import com.demo.cloud.dto.user.UpdateUserDto;
import com.demo.cloud.dto.user.UserViewDto;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserViewDto toViewDto(User user) {
        Organization org = user.getOrganization();
        return new UserViewDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName(),
                org == null ? null : org.getId()
        );
    }

    public User toModel(AddUserDto dto) {
        return new User(
                dto.getName(),
                dto.getSurname(),
                dto.getEmail(),
                dto.getUsername(),
                dto.getPassword(),
                false,
                null,
                null
        );
    }

    public User toModel(UpdateUserDto dto) {
        return new User(
                dto.getName(),
                dto.getSurname(),
                dto.getEmail(),
                dto.getUsername(),
                "",
                false,
                null,
                null
        );
    }
}
