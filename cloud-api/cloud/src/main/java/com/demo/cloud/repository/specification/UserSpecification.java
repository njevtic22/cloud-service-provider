package com.demo.cloud.repository.specification;

import com.demo.cloud.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public class UserSpecification extends EntitySpecification {
    public static Specification<User> getSpec2(Map<String, String> filter) {
        return Specification.<User>where(attrLike(new String[]{"role", "name"}, filter.get("role")))
                .and(attrLike("surname", filter.get("surname")))
                .and(attrLike("email", filter.get("email")))
                .and(attrLike("username", filter.get("username")))
                .and(attrLike("name", filter.get("name")))
                .and(attrEqual("archived", Boolean.valueOf(filter.get("archived"))));
    }

    public static Specification<User> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
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
                case "archived" -> attrEqual(key, Boolean.valueOf(value));
                case "role"   -> attrLike(new String[]{"role", "name"}, value);
                default -> throw new IllegalArgumentException("Invalid filter key " + key);
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
}
