package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Drive;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DriveSpecification extends EntitySpecification2<Drive> {
    @Override
    public Specification<Drive> get(String key, String value) {
        final String[] orgKeys = {"organization", "name"};
        final String[] machineKeys = {"machine", "name"};

        return switch (key) {
            case "name", "type" -> attrLike(key, value);

            case "minCapacity" -> attrMin("capacity", value);
            case "maxCapacity" -> attrMax("capacity", value);

            case "organization" -> attrLike(orgKeys, value);
            case "machine" -> attrLike(machineKeys, value);
            case "attached" -> isAttached("machine", value);

            case "archived" -> attrEqual(key, Boolean.valueOf(value));
            default -> throw new IllegalArgumentException("Invalid filter key " + key);
        };
    }

    private Specification<Drive> isAttached(String key, String attachedStr) {
        if (attachedStr == null || attachedStr.isBlank()) {
            return null;
        }

        boolean attached = Boolean.parseBoolean(attachedStr);
        return attached ? attrNotNull(key) : attrNull(key);
    }
}
