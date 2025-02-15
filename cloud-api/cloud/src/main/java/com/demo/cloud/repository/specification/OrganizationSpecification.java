package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Organization;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.function.BiFunction;

public class OrganizationSpecification extends EntitySpecification {
    public static Specification<Organization> getSpec2(Map<String, String> filter) {
        return Specification.<Organization>where(attrLike("name", filter.get("name")))
                .and(attrLike("description", filter.get("description")));
    }

    public static Specification<Organization> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<Organization> getSpec(Map<String, String> filter) {
        BiFunction<String, String, Specification<Organization>> specParser = (key, value) -> {
            return switch (key) {
                case "name", "description" -> attrLike(key, value);
                case "archived" -> attrEqual(key, Boolean.valueOf(value));
                default -> throw new IllegalArgumentException("Invalid filter key " + key);
            };
        };
        return getSpec(filter, specParser);
    }
}
