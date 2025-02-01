package com.demo.cloud.faker;

import com.demo.cloud.model.Organization;
import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;

public class SqlUtil {
    public static String toSqlInsert(Organization organization) {
        return "insert into organizations(id, name, description, logo, archived) values (" +
                organization.getId() + ", '" +
                organization.getName() + "', '" +
                organization.getDescription() + "', '" +
                organization.getLogo() + "', " +
                organization.isArchived() + ");";
    }

    public static String toSqlInsert(Role role) {
        return "insert into roles (id, name) values (" + role.getId() + ", '" + role.getName() + "');";
    }

    public static String toSqlInsert(User user) {
        Long orgId = user.getOrganization() == null ? null : user.getOrganization().getId();
        return "insert into users(id, name, surname, email, username, password, archived, role_id, organization_id) values (" +
                user.getId() + ", '" +
                user.getName() + "', '" +
                user.getSurname() + "', '" +
                user.getEmail() + "', '" +
                user.getUsername() + "', '" +
                user.getPassword() + "', " +
                user.isArchived() + ", " +
                user.getRole().getId() + ", " +
                orgId + ");";
    }

    public static String toSqlAlterSequenceRestart(String sequenceName, long restartWith) {
        return "alter sequence " + sequenceName + " restart with " + restartWith + ";";
    }
}
