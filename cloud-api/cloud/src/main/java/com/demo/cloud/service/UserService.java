package com.demo.cloud.service;

import com.demo.cloud.model.User;

import java.util.Map;

public interface UserService extends EntityGetter<Long, User>, EntityDeleter<Long> {
    User add(User newUser, String repeatedPassword, String roleName, Long organizationId);

    User getByUsername(String username);

    User update(Long id, User changes, String roleName, Long organizationId);

    void changePassword(String oldPassword, String newPassword, String repeatedPassword);

    long count(Map<String, String> filter);
}
