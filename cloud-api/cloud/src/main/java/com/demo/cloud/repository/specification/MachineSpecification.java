package com.demo.cloud.repository.specification;

import com.demo.cloud.core.error.exceptions.FilterKeyException;
import com.demo.cloud.model.VirtualMachine;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MachineSpecification extends EntitySpecification<VirtualMachine> {
    private final String[] cpuKeys = {"category", "cpu"};
    private final String[] ramKeys = {"category", "ram"};
    private final String[] gpuKeys = {"category", "gpu"};
    private final String[] orgKeys = {"organization", "id"};

    @Override
    public Specification<VirtualMachine> get(String key, String value) {
        return switch (key) {
            case "name", "organization", "category" -> attrLike(key, value);

            case "minCpu", "minRam", "minGpu" -> attrMin(getKeys(key), value);
            case "maxCpu", "maxRam", "maxGpu" -> attrMax(getKeys(key), value);

            case "organizationId" -> attrEqual(orgKeys, value);
            case "active", "archived" -> attrEqual(key, Boolean.valueOf(value));
            default -> throw new FilterKeyException(key);
        };
    }

    private String[] getKeys(String key) {
        return switch (key) {
            case "minCpu", "maxCpu" -> cpuKeys;
            case "minRam", "maxRam" -> ramKeys;
            case "minGpu", "maxGpu" -> gpuKeys;
            default -> throw new FilterKeyException(key);
        };
    }
}
