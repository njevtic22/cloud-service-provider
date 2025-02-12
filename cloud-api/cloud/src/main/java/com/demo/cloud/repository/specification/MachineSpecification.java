package com.demo.cloud.repository.specification;

import com.demo.cloud.model.VirtualMachine;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public class MachineSpecification extends EntitySpecification {
    public static Specification<VirtualMachine> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<VirtualMachine> getSpec(Map<String, String> filter) {
        ArrayList<Specification<VirtualMachine>> specs = new ArrayList<>(filter.size());

        final String[] cpuKeys = getKeys("cpu");
        final String[] ramKeys = getKeys("ram");
        final String[] gpuKeys = getKeys("gpu");

        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Specification<VirtualMachine> spec = switch (key) {
                case "name", "organization", "category", "username" -> attrLike(key, value);

                case "minCpu" -> attrMin(cpuKeys, value);
                case "minRam" -> attrMin(ramKeys, value);
                case "minGpu" -> attrMin(gpuKeys, value);

                case "maxCpu" -> attrMax(cpuKeys, value);
                case "maxRam" -> attrMax(ramKeys, value);
                case "maxGpu" -> attrMax(gpuKeys, value);

                case "organizationId" -> attrEqual(new String[]{"organization", "id"}, value);
                case "archived" -> attrEqual(key, Boolean.valueOf(value));
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

    private static String[] getKeys(String finalKey) {
        return new String[]{"category", finalKey};
    }
}
