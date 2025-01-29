package com.demo.cloud.service;

import com.demo.cloud.model.User;

public interface UserAdder {
    User add(User newUser, String repeatedPassword, String roleName, Long organizationId);
}
