package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Organization;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrganizationSpecification extends EntitySpecification2<Organization> {
    public Specification<Organization> getSpec2(Map<String, String> filter) {
        return Specification.<Organization>where(attrLike("name", filter.get("name")))
                .and(attrLike("description", filter.get("description")));
    }

    @Override
    public Specification<Organization> get(String key, String value) {
        return switch (key) {
            case "name", "description" -> attrLike(key, value);
            case "archived" -> attrEqual(key, Boolean.valueOf(value));
            default -> throw new IllegalArgumentException("Invalid filter key " + key);
        };
    }
}
