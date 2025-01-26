package com.demo.cloud.faker;

import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;

public class SqlUtil {
    public static String toSqlInsert(Role role) {
        return "insert into roles (id, name) values (" + role.getId() + ", '" + role.getName() + "');";
    }

    public static String toSqlInsert(User user) {
        return "insert into users(id, name, surname, email, username, password, archived, role_id) values (" +
                user.getId() + ", '" +
                user.getName() + "', '" +
                user.getSurname() + "', '" +
                user.getEmail() + "', '" +
                user.getUsername() + "', '" +
                user.getPassword() + "', " +
                user.isArchived() + ", " +
                user.getRole().getId() + ");";
    }

    public static String toSqlAlterSequenceRestart(String sequenceName, long restartWith) {
        return "alter sequence " + sequenceName + " restart with " + restartWith + ";";
    }
}
