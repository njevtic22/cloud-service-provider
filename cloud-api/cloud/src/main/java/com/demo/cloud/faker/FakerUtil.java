package com.demo.cloud.faker;

import com.demo.cloud.model.Role;

public class FakerUtil {
    public static int getUsersPerRole(Role role, int superAdmins, int admins, int users) {
        return switch (role.getName()) {
            case "ROLE_SUPER_ADMIN" -> superAdmins;
            case "ROLE_ADMIN" -> admins;
            case "ROLE_USER" -> users;
            default -> throw new IllegalArgumentException("Invalid role");
        };
    }

    public static String getRoleUsername(Role role) {
        return switch (role.getName()) {
            case "ROLE_SUPER_ADMIN" -> "superadmin";
            case "ROLE_ADMIN" -> "admin";
            case "ROLE_USER" -> "user";
            default -> throw new IllegalArgumentException("Invalid role");
        };
    }
    public static String escapeApostrophe(String str) {
        return str.replaceAll("'", "''");
    }
}
