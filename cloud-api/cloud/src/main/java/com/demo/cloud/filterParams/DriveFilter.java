package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class DriveFilter extends FilterParams {
    @ConstructorProperties({"name", "min-capacity", "max-capacity", "type", "organization", "machine", "attached"})
    public DriveFilter(
            String name,
            Integer minCapacity,
            Integer maxCapacity,
            String type,
            String organization,
            String machine,
            Boolean attached
    ) {
        putIfNotNull(name, minCapacity, maxCapacity, type, organization, machine, attached);
    }
}
