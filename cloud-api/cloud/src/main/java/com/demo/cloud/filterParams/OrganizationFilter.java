package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class OrganizationFilter extends FilterParams {
    @ConstructorProperties({"name", "description"})
    public OrganizationFilter(String name, String description) {
        putIfNotNull("name", name);
        putIfNotNull("description", description);
    }
}
