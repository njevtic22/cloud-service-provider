package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class CategoryFilter extends FilterParams {
    @ConstructorProperties({"name", "min-cpu", "max-cpu", "min-ram", "max-ram", "min-gpu", "max-gpu"})
    public CategoryFilter(
            String name,
            Integer minCpu,
            Integer maxCpu,
            Integer minRam,
            Integer maxRam,
            Integer minGpu,
            Integer maxGpu
    ) {
        putIfNotNull("name", name);
        putIfNotNull("minCpu", minCpu);
        putIfNotNull("maxCpu", maxCpu);
        putIfNotNull("minRam", minRam);
        putIfNotNull("maxRam", maxRam);
        putIfNotNull("minGpu", minGpu);
        putIfNotNull("maxGpu", maxGpu);
    }
}
