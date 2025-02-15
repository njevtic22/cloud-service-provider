package com.demo.cloud.faker;

import com.demo.cloud.model.Activity;
import com.demo.cloud.model.Category;
import com.demo.cloud.model.Drive;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;
import com.demo.cloud.model.VirtualMachine;

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

    public static String toSqlInsert(Category cat) {
        return "insert into categories(id, name, cpu, ram, gpu, archived) values (" +
                cat.getId() + ", '" +
                cat.getName() + "', " +
                cat.getCpu() + ", " +
                cat.getRam() + ", " +
                cat.getGpu() + ", " +
                cat.isArchived() + ");";
    }

    public static String toSqlInsert(VirtualMachine machine) {
        return "insert into virtual_machines(id, name, archived, organization_id, category_id) values (" +
                machine.getId() + ", '" +
                machine.getName() + "', " +
                machine.isArchived() + ", " +
                machine.getOrganization().getId() + ", " +
                machine.getCategory().getId() + ");";
    }

    public static String toSqlInsert(Drive drive) {
        Long machineId = drive.getMachine() == null ? null : drive.getMachine().getId();
        return "insert into drives(id, name, capacity, type, archived, organization_id, machine_id) values (" +
                drive.getId() + ", '" +
                drive.getName() + "', " +
                drive.getCapacity() + ", '" +
                drive.getType().toString() + "', " +
                drive.isArchived() + ", " +
                drive.getOrganization().getId() + ", " +
                machineId + ");";
    }

    public static String toSqlInsert(Activity activity) {
        String turnedOff = activity.getTurnedOff() == null ? null : "'" + activity.getTurnedOff() + "'";
        return "insert into activities(id, turned_on, turned_off, profit, machine_id) values (" +
                activity.getId() + ", '" +
                activity.getTurnedOn() + "', " +
                turnedOff + ", " +
                activity.getProfit() + ", " +
                activity.getMachine().getId() + ");";
    }

    public static String toSqlAlterSequenceRestart(String sequenceName, long restartWith) {
        return "alter sequence " + sequenceName + " restart with " + restartWith + ";";
    }
}
