package com.demo.cloud.repository.specification;

import com.demo.cloud.core.error.exceptions.FilterKeyException;
import com.demo.cloud.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserSpecification extends EntitySpecification<User> {
    private final String[] roleKeys = {"role", "name"};

    public Specification<User> get2(Map<String, String> filter) {
        return Specification.<User>where(attrLike(roleKeys, filter.get("role")))
                .and(attrLike("surname", filter.get("surname")))
                .and(attrLike("email", filter.get("email")))
                .and(attrLike("username", filter.get("username")))
                .and(attrLike("name", filter.get("name")))
                .and(attrEqual("archived", Boolean.valueOf(filter.get("archived"))));
    }

    // Creates only those specifications which are not null (and do not return null) unlike getSpec2
    // Is it faster though?
    @Override
    public Specification<User> get(String key, String value) {
        return switch (key) {
            case "name", "surname", "email", "username" -> attrLike(key, value);
            case "role" -> attrLike(roleKeys, value);
            case "archived" -> attrEqual(key, Boolean.valueOf(value));
            default -> throw new FilterKeyException(key);
        };
    }
}
