package com.demo.cloud.faker;

import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;
import com.demo.cloud.util.LongGenerator;
import com.github.javafaker.Faker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.demo.cloud.faker.FakerUtil.escapeApostrophe;
import static com.demo.cloud.faker.FakerUtil.getRoleUsername;
import static com.demo.cloud.faker.FakerUtil.getUsersPerRole;
import static com.demo.cloud.faker.SqlUtil.toSqlAlterSequenceRestart;

public class FakeDatabaseGenerator {
    private final Faker faker = new Faker();

    private final String LINE = "-";
    private final String LINES = LINE.repeat(200);

    private final int ROLES = 3;
    private final int SUPER_ADMINS = 5;
    private final int ADMINS = 10;
    private final int USERS = 20;

    private final String encodedPassword = "$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6";

    private final PrintWriter out = new PrintWriter(new FileWriter("./src/main/resources/data.sql"));

    public FakeDatabaseGenerator() throws IOException {
    }

    private void generateHeader(PrintWriter out) {
        out.println("-- Passwords are hashed using BCrypt algorithm https://bcrypt-generator.com/");
        out.println("-- Passwords for all users are:");
        out.println("--");

        out.println("-- Scripts combined generate database for cloud-service-provider");
        out.println("-- It generates:");
        out.println("--\t- " + SUPER_ADMINS + " super admins");
        out.println("--\t- " + ADMINS + " admins");
        out.println("--\t- " + USERS + " users");
        out.println("--");

        out.flush();
    }

    public void generate() {
        generateHeader(out);

        // generating roles
        LongGenerator roleId = new LongGenerator();
        Map<Long, Role> roles = generateRoles(roleId);

        // generating users
        LongGenerator userId = new LongGenerator();
        Map<Long, User> users = generateUsers(new ArrayList<>(roles.values()), userId);


        ////////////////////

        // inserting roles
        printToSqlInsert(roles.values(), "inserting roles", out, SqlUtil::toSqlInsert);
        // altering role_id_seq
        printSequenceRestart(ROLES, roleId, "role_id_seq", out);

        // inserting users
        printToSqlInsert(users.values(), "inserting users", out, SqlUtil::toSqlInsert);
        // altering user_id_seq
        printSequenceRestart(SUPER_ADMINS + ADMINS + USERS, userId, "user_id_seq", out);

        out.close();
    }

    private Map<Long, Role> generateRoles(LongGenerator roleId) {
        Role superAdmin = createRole(roleId.next(), "ROLE_SUPER_ADMIN");
        Role admin = createRole(roleId.next(), "ROLE_ADMIN");
        Role user = createRole(roleId.next(), "ROLE_USER");

        LinkedHashMap<Long, Role> roles = new LinkedHashMap<>();
        roles.put(superAdmin.getId(), superAdmin);
        roles.put(admin.getId(), admin);
        roles.put(user.getId(), user);

        return roles;
    }

    private Role createRole(Long roleId, String roleName) {
        Role createdRole = new Role(roleName);

        try {
            Class<Role> roleClass = Role.class;
            Field id = roleClass.getDeclaredField("id");
            id.setAccessible(true);

            id.set(createdRole, roleId);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return createdRole;
    }

    private Map<Long, User> generateUsers(List<Role> roles, LongGenerator userId) {
        LinkedHashMap<Long, User> users = new LinkedHashMap<>(SUPER_ADMINS + ADMINS + USERS);

        for (Role role : roles) {
            int usersPerRole = getUsersPerRole(role, SUPER_ADMINS, ADMINS, USERS);
            String roleUsername = getRoleUsername(role);

            for (int i = 0; i < usersPerRole; i++) {
                User user = new User(
                        userId.next(),
                        faker.name().firstName(),
                        escapeApostrophe(faker.name().lastName()),
                        roleUsername + (i + 1) + "@gmail.com",
                        roleUsername + (i + 1),
                        encodedPassword,
                        false,
                        role
                );
                users.put(userId.current(), user);
            }
        }

        return users;
    }

    private <T> void printToSqlInsert(Collection<T> values, String linesDescription, PrintWriter out, Function<T, String> fun) {
        printStartLines(linesDescription, out);

        for (T value : values) {
            out.println(fun.apply(value));
        }

        printEndLines(out);
    }

    private void printSequenceRestart(final long OBJECT_NUM, LongGenerator objectId, String sequenceName, PrintWriter out) {
        if (OBJECT_NUM != objectId.current()) {
            throw new AssertionError("OBJECT_NUM != objectId.current() for: " + sequenceName);
        }

        printStartLines("Altering " + sequenceName, out);
        out.println(toSqlAlterSequenceRestart(sequenceName, OBJECT_NUM + 1));
        printEndLines(out);
    }

    private void printStartLines(String description, PrintWriter out) {
        int descLength = description.length() + 2;
        int remainingLength = LINES.length() - descLength;

        int firstHalf = remainingLength / 2;
        int secondHalf = remainingLength - firstHalf;   // if length is odd

        String line = LINE.repeat(firstHalf) + " " + description + " " + LINE.repeat(secondHalf);

        out.println(LINES);
        out.println(line);
    }

    private void printEndLines(PrintWriter out) {
        out.println(LINES);
        out.println(LINES);
        out.println();
    }

    public static void main(String[] args) throws IOException {
        new FakeDatabaseGenerator().generate();
    }
}
