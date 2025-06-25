package com.demo.cloud.filterParams;

import java.beans.ConstructorProperties;

public class UserFilter extends FilterParams {
    @ConstructorProperties({"name","surname", "email", "username", "role", "organization"})
    public UserFilter(String name, String surname, String email, String username, String role, String organization) {
        putIfNotNull("name", name);
        putIfNotNull("surname", surname);
        putIfNotNull("email", email);
        putIfNotNull("username", username);
        putIfNotNull("role", role);
        putIfNotNull("organization", organization);
    }
}
