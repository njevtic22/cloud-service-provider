package com.demo.cloud.repository.specification;

import com.demo.cloud.model.VirtualMachine;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class MachineSpecification extends EntitySpecification {
    public static Specification<VirtualMachine> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<VirtualMachine> getSpec(Map<String, String> filter) {
        return orgIdEqual(filter.get("organizationId"));

//        ArrayList<Specification<VirtualMachine>> specs = new ArrayList<>(filter.size());
//
//        for (Map.Entry<String, String> entry : filter.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            Specification<VirtualMachine> spec = switch (key) {
//                case "name", "organization", "category", "username" -> attrLike(key, value);
//                case "minCpu", "minRam", "minGpu" -> attrMin(key, value);
//                case "maxCpu", "maxRam", "maxGpu" -> attrMax(key, value);
//                case "archived" -> attrEqual(key, value);
//                default -> throw new IllegalArgumentException("Invalid filter key " + key);
//            };
//
//            if (spec != null) {
//                specs.add(spec);
//            }
//        }
//
//        return specs
//                .stream()
//                .reduce(Specification::and)
//                .orElse(null);
    }

    public static Specification<VirtualMachine> orgIdEqual(String strId) {
        if (strId == null || strId.isBlank()) {
            return null;
        }

        Long orgId = Long.valueOf(strId);

        return (root, query, cb) -> cb.equal(root.get("organization").get("id"), orgId);
    }
}
