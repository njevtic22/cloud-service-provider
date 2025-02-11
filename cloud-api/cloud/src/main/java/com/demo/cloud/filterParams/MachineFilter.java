package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class MachineFilter extends FilterParams {
    @ConstructorProperties({"name", "organization", "category", "min-cpu", "max-cpu", "min-ram", "max-ram", "min-gpu", "max-gpu"})
    public MachineFilter(
            String name,
            String organization,
            String category,
            Integer minCpu,
            Integer maxCpu,
            Integer minRam,
            Integer maxRam,
            Integer minGpu,
            Integer maxGpu
    ) {
        putIfNotNull("name", name);
        putIfNotNull("organization", organization);
        putIfNotNull("category", category);
        putIfNotNull("minCpu", minCpu);
        putIfNotNull("maxCpu", maxCpu);
        putIfNotNull("minRam", minRam);
        putIfNotNull("maxRam", maxRam);
        putIfNotNull("minGpu", minGpu);
        putIfNotNull("maxGpu", maxGpu);
    }
}
