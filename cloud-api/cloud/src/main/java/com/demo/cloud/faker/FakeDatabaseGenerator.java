package com.demo.cloud.faker;

import com.demo.cloud.model.Category;
import com.demo.cloud.model.Drive;
import com.demo.cloud.model.Organization;
import com.demo.cloud.model.Role;
import com.demo.cloud.model.User;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.util.CycleIterator;
import com.demo.cloud.util.LongGenerator;
import com.github.javafaker.Faker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import static com.demo.cloud.faker.FakerUtil.escapeApostrophe;
import static com.demo.cloud.faker.FakerUtil.generateLorem;
import static com.demo.cloud.faker.FakerUtil.getCapacity;
import static com.demo.cloud.faker.FakerUtil.getCategoryDataIterator;
import static com.demo.cloud.faker.FakerUtil.getOrgImagesPathIterator;
import static com.demo.cloud.faker.FakerUtil.getType;
import static com.demo.cloud.faker.SqlUtil.toSqlAlterSequenceRestart;

public class FakeDatabaseGenerator {
    private final Faker faker = new Faker();

    private final ResourceBundle bundle = ResourceBundle.getBundle("application");
    private final String rootImages = bundle.getString("root.images");

    private final String LINE = "-";
    private final String LINES = LINE.repeat(200);

    private final int LOREM_LENGTH = 1000;

    private final int ORGANIZATIONS = 5;
    private final int ADMINS_PER_ORGANIZATION = 5;
    private final int USERS_PER_ORGANIZATION = 10;

    private final int ROLES = 3;
    private final int SUPER_ADMINS = 5;
    private final int ADMINS = ORGANIZATIONS * ADMINS_PER_ORGANIZATION;
    private final int USERS = ORGANIZATIONS * USERS_PER_ORGANIZATION;

    private final int CATEGORIES = 25;
    private final int MACHINES_PER_ORGANIZATION = 200;
    private final int MACHINES = ORGANIZATIONS * MACHINES_PER_ORGANIZATION;

    private final int DRIVES_PER_ORGANIZATION = 500;
    private final int DRIVES = ORGANIZATIONS * DRIVES_PER_ORGANIZATION;
    private final int DRIVES_PER_MACHINE = 2; // always check number of machines and drives per organization

    private final String encodedPassword = "$2a$10$JCYrt8QGHg4suBXWiRgjKu93h5DCq3yFDXMDsTY/Itkgeu3h3pCE6";

    private final PrintWriter mOut = new PrintWriter(new FileWriter("./src/main/resources/organizations_roles_users_categories_machines.sql"));
    private final PrintWriter dOut = new PrintWriter(new FileWriter("./src/main/resources/drives_activities.sql"));

    private CycleIterator<Category> categories;
    private final Iterator<int[]> categoryData = getCategoryDataIterator("category-data.csv");
    private final Iterator<String> orgImages = getOrgImagesPathIterator(rootImages + "/organization");

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
        out.println("--\t- " + ORGANIZATIONS + " organizations");
        out.println("--\t\t- " + ADMINS_PER_ORGANIZATION + " admins per organization");
        out.println("--\t\t- " + USERS_PER_ORGANIZATION + " users per organization");
        out.println("--\t\t- " + MACHINES_PER_ORGANIZATION + " machines per organization");
        out.println("--\t\t- " + DRIVES_PER_ORGANIZATION + " drives per organization");
        out.println("--\t- " + CATEGORIES + " categories");
        out.println("--\t- " + MACHINES + " machines");
        out.println("--\t- " + DRIVES + " drives");
        out.println("--");

        out.flush();
    }

    public void generate() {
        generateHeader(mOut);
        generateHeader(dOut);

        // generating organizations
        LongGenerator orgId = new LongGenerator();
        Map<Long, Organization> orgs = generateOrganizations(orgId);

        // generating roles
        LongGenerator roleId = new LongGenerator();
        Map<Long, Role> roles = generateRoles(roleId);

        // generating users
        LongGenerator userId = new LongGenerator();
        Map<Long, User> users = generateUsers(roles, orgs, userId);

        // generating categories
        LongGenerator catId = new LongGenerator();
        Map<Long, Category> cats = generateCategories(catId);

        // generating virtual machines
        LongGenerator machineId = new LongGenerator();
        Map<Organization, List<VirtualMachine>> orgMachines = createOrgMachineMap(orgs);
        Map<Long, VirtualMachine> machines = generateMachines(orgs, orgMachines, machineId);

        // generating drives
        LongGenerator driveId = new LongGenerator();
        Map<Organization, List<Drive>> orgDrives = createOrgDriveMap(orgs);
        Map<Long, Drive> drives = generateDrives(orgs, orgDrives, driveId);

        // connecting drives and machines
        connectDrivesMachines(orgMachines, orgDrives);

        ////////////////////

        // inserting organizations
        printToSqlInsert(orgs.values(), "inserting organizations", mOut, SqlUtil::toSqlInsert);
        // altering org_id_seq
        printSequenceRestart(ORGANIZATIONS, orgId, "organization_id_seq", mOut);

        // inserting roles
        printToSqlInsert(roles.values(), "inserting roles", mOut, SqlUtil::toSqlInsert);
        // altering role_id_seq
        printSequenceRestart(ROLES, roleId, "role_id_seq", mOut);

        // inserting users
        printToSqlInsert(users.values(), "inserting users", mOut, SqlUtil::toSqlInsert);
        // altering user_id_seq
        printSequenceRestart(SUPER_ADMINS + ADMINS + USERS, userId, "user_id_seq", mOut);

        // inserting categories
        printToSqlInsert(cats.values(), "inserting categories", mOut, SqlUtil::toSqlInsert);
        // altering category_id_seq
        printSequenceRestart(CATEGORIES, catId, "category_id_seq", mOut);

        // inserting machines
        printToSqlInsert(machines.values(), "inserting machines", mOut, SqlUtil::toSqlInsert);
        // altering machine_id_seq
        printSequenceRestart(MACHINES, machineId, "machine_id_seq", mOut);

        // inserting drives
        printToSqlInsert(drives.values(), "inserting drives", dOut, SqlUtil::toSqlInsert);
        // altering drive_id_seq
        printSequenceRestart(DRIVES, driveId, "drive_id_seq", dOut);

        mOut.close();
        dOut.close();
    }

    private Map<Long, Organization> generateOrganizations(LongGenerator orgId) {
        LinkedHashMap<Long, Organization> orgs = new LinkedHashMap<>(ORGANIZATIONS);

        for (int i = 0; i < ORGANIZATIONS; i++) {
            Organization org = new Organization(
                    orgId.next(),
                    orgId.current() + ": " + faker.dragonBall().character(),
                    generateLorem(faker, LOREM_LENGTH),
                    orgImages.next(),
                    false
            );
            orgs.put(org.getId(), org);
        }

        return orgs;
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

    private Map<Long, User> generateUsers(Map<Long, Role> roles, Map<Long, Organization> orgs, LongGenerator userId) {
        LinkedHashMap<Long, User> users = new LinkedHashMap<>(SUPER_ADMINS + ADMINS + USERS);

        Map<Long, User> superAdmins = generateSuperAdmins(roles.get(1L), userId);
        users.putAll(superAdmins);

        Map<Long, User> admins = generateAdmins(roles.get(2L), orgs, userId);
        users.putAll(admins);

        Map<Long, User> regulars = generateUsers(roles.get(3L), orgs, userId);
        users.putAll(regulars);

        return users;
    }

    private Map<Long, User> generateSuperAdmins(Role superRole, LongGenerator userId) {
        LinkedHashMap<Long, User> superAdmins = new LinkedHashMap<>(SUPER_ADMINS);

        for (int i = 0; i < SUPER_ADMINS; i++) {
            User user = createUser(userId.next(), "superadmin" + (i + 1), superRole, null);
            superAdmins.put(userId.current(), user);
        }

        return superAdmins;
    }

    private Map<Long, User> generateAdmins(Role adminRole, Map<Long, Organization> orgs, LongGenerator userId) {
        LinkedHashMap<Long, User> admins = new LinkedHashMap<>(ADMINS);

        for (int i = 0; i < ORGANIZATIONS; i++) {
            Organization org = orgs.get(i + 1L);
            for (int j = 0; j < ADMINS_PER_ORGANIZATION; j++) {
                User user = createUser(userId.next(), "admin" + (i * ADMINS_PER_ORGANIZATION + j + 1), adminRole, org);
                admins.put(userId.current(), user);
            }
        }

        return admins;
    }

    private Map<Long, User> generateUsers(Role userRole, Map<Long, Organization> orgs, LongGenerator userId) {
        LinkedHashMap<Long, User> users = new LinkedHashMap<>(USERS);

        for (int i = 0; i < ORGANIZATIONS; i++) {
            Organization org = orgs.get(i + 1L);
            for (int j = 0; j < USERS_PER_ORGANIZATION; j++) {
                User user = createUser(userId.next(), "user" + (i * USERS_PER_ORGANIZATION + j + 1), userRole, org);
                users.put(userId.current(), user);
            }
        }

        return users;
    }

    private User createUser(Long id, String username, Role userRole, Organization org) {
        return new User(
                id,
                faker.name().firstName(),
                escapeApostrophe(faker.name().lastName()),
                username + "@gmail.com",
                username,
                encodedPassword,
                false,
                userRole,
                org
        );
    }

    private Map<Long, Category> generateCategories(LongGenerator catId) {
        LinkedHashMap<Long, Category> cats = new LinkedHashMap<>(CATEGORIES);

        for (int i = 0; i < CATEGORIES; i++) {
            int[] data = categoryData.next();
            Category cat = new Category(
                    catId.next(),
                    catId.current() + ": " + escapeApostrophe(faker.lordOfTheRings().location()),
                    data[0],
                    data[1],
                    data[2],
                    false
            );
            cats.put(cat.getId(), cat);
        }

        categories = new CycleIterator<>(cats.values().toArray(new Category[0]));

        return cats;
    }

    private Map<Long, VirtualMachine> generateMachines(Map<Long, Organization> orgs, Map<Organization, List<VirtualMachine>> orgMachines, LongGenerator machineId) {
        LinkedHashMap<Long, VirtualMachine> machines = new LinkedHashMap<>(MACHINES);

        for (int i = 0; i < ORGANIZATIONS; i++) {
            Organization org = orgs.get(i + 1L);
            for (int j = 0; j < MACHINES_PER_ORGANIZATION; j++) {
                List<VirtualMachine> machinesPerOrg = orgMachines.get(org);
                VirtualMachine machine = new VirtualMachine(
                        machineId.next(),
                        machineId.current() + ": " + faker.lordOfTheRings().character(),
                        false,
                        org,
                        categories.next()
                );

                machinesPerOrg.add(machine);
                machines.put(machine.getId(), machine);
            }
        }

        return machines;
    }

    private Map<Organization, List<VirtualMachine>> createOrgMachineMap(Map<Long, Organization> orgs) {
        HashMap<Organization, List<VirtualMachine>> orgMachines = new HashMap<>();

        for (Organization org : orgs.values()) {
            orgMachines.put(org, new ArrayList<>());
        }

        return orgMachines;
    }

    private Map<Organization, List<Drive>> createOrgDriveMap(Map<Long, Organization> orgs) {
        HashMap<Organization, List<Drive>> orgDrives = new HashMap<>();

//        for (int i = 0; i < ORGANIZATIONS; i++) {
//            Organization org = orgs.get(i + 1L);
//            orgDrives.put(org, new ArrayList<>());
//        }

        for (Organization org : orgs.values()) {
            orgDrives.put(org, new ArrayList<>());
        }

        return orgDrives;
    }

    private Map<Long, Drive> generateDrives(Map<Long, Organization> orgs, Map<Organization, List<Drive>> orgDrives, LongGenerator driveId) {
        LinkedHashMap<Long, Drive> drives = new LinkedHashMap<>(DRIVES);

        for (int i = 0; i < ORGANIZATIONS; i++) {
            Organization org = orgs.get(i + 1L);
            for (int j = 0; j < DRIVES_PER_ORGANIZATION; j++) {
                List<Drive> drivesPerOrg = orgDrives.get(org);
                Drive drive = new Drive(
                        driveId.next(),
                        driveId.current() + ": " + faker.backToTheFuture().character(),
                        getCapacity(faker),
                        getType(faker),
                        false,
                        org,
                        null
                );

                drivesPerOrg.add(drive);
                drives.put(drive.getId(), drive);
            }
        }

        return drives;
    }

    private void connectDrivesMachines(Map<Organization, List<VirtualMachine>> orgMachines, Map<Organization, List<Drive>> orgDrives) {
        for (Map.Entry<Organization, List<VirtualMachine>> entry : orgMachines.entrySet()) {
            Organization org = entry.getKey();
            List<VirtualMachine> machines = entry.getValue();
            List<Drive> drives = new ArrayList<>(orgDrives.get(org));

            for (VirtualMachine toConnect : machines) {
                for (int i = 0; i < DRIVES_PER_MACHINE; i++) {
                    Drive removed = drives.remove(0);
                    connect(removed, toConnect);
                }
            }
        }
    }

    private void connect(Drive drive, VirtualMachine machine) {
        try {
            Field machineField = Drive.class.getDeclaredField("machine");
            machineField.setAccessible(true);

            machineField.set(drive, machine);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
