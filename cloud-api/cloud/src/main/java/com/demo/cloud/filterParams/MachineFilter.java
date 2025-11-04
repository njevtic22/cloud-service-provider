package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class MachineFilter extends FilterParams {
    @ConstructorProperties({"name", "organization", "category", "min-cpu", "max-cpu", "min-ram", "max-ram", "min-gpu", "max-gpu", "active"})
    public MachineFilter(
            String name,
            String organization,
            String category,
            Integer minCpu,
            Integer maxCpu,
            Integer minRam,
            Integer maxRam,
            Integer minGpu,
            Integer maxGpu,
            Boolean active
    ) {
        putIfNotNull(name, organization, category, minCpu, maxCpu, minRam, maxRam, minGpu, maxGpu, active);
    }
}
