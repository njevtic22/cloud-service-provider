package com.demo.cloud.repository.specification;

import com.demo.cloud.model.VirtualMachine;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.function.BiFunction;

public class MachineSpecification extends EntitySpecification {
    public static Specification<VirtualMachine> getSpec(Map<String, String> filter, boolean archived) {
        return getSpec(withArchived(filter, archived));
    }

    public static Specification<VirtualMachine> getSpec(Map<String, String> filter) {
        final String[] cpuKeys = getKeys("cpu");
        final String[] ramKeys = getKeys("ram");
        final String[] gpuKeys = getKeys("gpu");

        BiFunction<String, String, Specification<VirtualMachine>> specParser = (key, value) -> {
            return switch (key) {
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
        };

        return getSpec(filter, specParser);
    }

    private static String[] getKeys(String finalKey) {
        return new String[]{"category", finalKey};
    }
}
