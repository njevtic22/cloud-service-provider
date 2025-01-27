package com.demo.cloud.repository.specification;

import com.demo.cloud.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public class UserSpecification extends ClassSpecification {
    public static Specification<User> getSpec2(Map<String, String> filter) {
        return Specification.where(roleLike(filter.get("role")))
                .and(attrLike("surname", filter.get("surname")))
                .and(attrLike("email", filter.get("email")))
                .and(attrLike("username", filter.get("username")))
                .and(attrLike("name", filter.get("name")));
    }

    // Creates only those specifications which are not null (and do not return null) unlike getSpec2
    // Is it faster though?
    public static Specification<User> getSpec(Map<String, String> filter) {
        ArrayList<Specification<User>> specs = new ArrayList<>(filter.size());

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Specification<User> spec = switch (key) {
                case "name", "surname", "email", "username" -> attrLike(key, value);
                case "role"   -> roleLike(value);
                default -> throw new IllegalArgumentException("Invalid filter key");
            };

            if (spec != null) {
                specs.add(spec);
            }
        }

        return specs
                .stream()
                .reduce(Specification::and)
                .orElse(null);
    }

    public static Specification<User> roleLike(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.like(cb.upper(root.get("role").get("name")), "%" + keyword.toUpperCase() + "%");
    }
}
