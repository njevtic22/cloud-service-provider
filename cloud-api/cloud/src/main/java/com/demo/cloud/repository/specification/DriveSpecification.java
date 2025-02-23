package com.demo.cloud.repository.specification;

import com.demo.cloud.model.Drive;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.function.BiFunction;

public class DriveSpecification extends EntitySpecification {
    public static Specification<Drive> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<Drive> getSpec(Map<String, String> filter) {
        final String[] orgKeys = {"organization", "name"};
        final String[] machineKeys = {"machine", "name"};

        BiFunction<String, String, Specification<Drive>> specParser = (key, value) -> {
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
        };

        return getSpec(filter, specParser);
    }

    private static Specification<Drive> isAttached(String key, String attachedStr) {
        if (attachedStr == null || attachedStr.isBlank()) {
            return null;
        }

        boolean attached = Boolean.parseBoolean(attachedStr);
        return attached ? attrNotNull(key) : attrNull(key);
    }
}
