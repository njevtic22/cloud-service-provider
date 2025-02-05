package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Organization;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public class OrganizationSpecification extends EntitySpecification {
    public static Specification<Organization> getSpec2(Map<String, String> filter) {
        return Specification.<Organization>where(attrLike("name", filter.get("name")))
                .and(attrLike("description", filter.get("description")));
    }

    public static Specification<Organization> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<Organization> getSpec(Map<String, String> filter) {
        ArrayList<Specification<Organization>> specs = new ArrayList<>(filter.size());

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Specification<Organization> spec = switch (key) {
                case "name", "description" -> attrLike(key, value);
                case "archived" -> attrEqual(key, value);
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
