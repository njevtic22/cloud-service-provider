package com.demo.cloud.service;

import com.demo.cloud.model.Role;

public interface RoleService extends EntityGetter<Long, Role> {
    Role getByName(String name);
}
