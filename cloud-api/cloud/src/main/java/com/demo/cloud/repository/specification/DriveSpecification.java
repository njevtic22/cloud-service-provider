package com.demo.cloud.repository.specification;

import com.demo.cloud.core.error.exceptions.FilterKeyException;
import com.demo.cloud.model.Drive;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DriveSpecification extends EntitySpecification<Drive> {
    private final String[] orgKeys = {"organization", "id"};
    private final String[] orgNameKeys = {"organization", "name"};
    private final String[] machineKeys = {"machine", "name"};
    private final String[] nameKey = {"name"};
    private final String[] typeKey = {"type"};

    @Override
    public Specification<Drive> get(String key, String value) {
        return switch (key) {
            case "name", "type", "organization", "machine" -> attrLike(getKeys(key), value);
            case "organizationId" -> attrEqual(orgKeys, value);

            case "minCapacity" -> attrMin("capacity", value);
            case "maxCapacity" -> attrMax("capacity", value);

            case "attached" -> isAttached("machine", value);
            case "archived" -> attrEqual(key, Boolean.valueOf(value));
            default -> throw new FilterKeyException(key);
        };
    }

    private Specification<Drive> isAttached(String key, String attachedStr) {
        if (attachedStr == null || attachedStr.isBlank()) {
            return null;
        }

        boolean attached = Boolean.parseBoolean(attachedStr);
        return attached ? attrNotNull(key) : attrNull(key);
    }

    private String[] getKeys(String key) {
        return switch (key) {
            case "name" -> nameKey;
            case "type" -> typeKey;
            case "organization" -> orgNameKeys;
            case "machine" -> machineKeys;
            default -> throw new FilterKeyException(key);
        };
    }
}
