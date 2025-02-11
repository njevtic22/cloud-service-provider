package com.demo.cloud.repository.specification;

import com.demo.cloud.model.VirtualMachine;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public class MachineSpecification extends EntitySpecification {
    public static Specification<VirtualMachine> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<VirtualMachine> getSpec(Map<String, String> filter) {
        ArrayList<Specification<VirtualMachine>> specs = new ArrayList<>(filter.size());

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Specification<VirtualMachine> spec = switch (key) {
                case "name", "organization", "category", "username" -> attrLike(key, value);

                case "minCpu" -> attrMin(new String[]{"category", "cpu"}, value);
                case "minRam" -> attrMin(new String[]{"category", "ram"}, value);
                case "minGpu" -> attrMin(new String[]{"category", "gpu"}, value);

                case "maxCpu" -> attrMax(new String[]{"category", "cpu"}, value);
                case "maxRam" -> attrMax(new String[]{"category", "ram"}, value);
                case "maxGpu" -> attrMax(new String[]{"category", "gpu"}, value);

                case "organizationId" -> orgIdEqual(value);
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

    public static Specification<VirtualMachine> orgIdEqual(String strId) {
        if (strId == null || strId.isBlank()) {
            return null;
        }

        Long orgId = Long.valueOf(strId);

        return (root, query, cb) -> cb.equal(root.get("organization").get("id"), orgId);
    }

    private static Specification<VirtualMachine> attrMin(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.greaterThanOrEqualTo(getPath(root, keys), value);
    }

    private static Specification<VirtualMachine> attrMax(String[] keys, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return (root, query, cb) -> cb.lessThanOrEqualTo(getPath(root, keys), value);
    }

    private static Path<String> getPath(Root<VirtualMachine> root, String... keys) {
        Path<String> path = root.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            path = path.get(keys[i]);
        }
        return path;
    }
}
