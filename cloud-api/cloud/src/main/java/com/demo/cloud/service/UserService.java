package com.demo.cloud.service;

import com.demo.cloud.model.User;

public interface UserService extends UserAdder, EntityGetter<Long, User> {
}
