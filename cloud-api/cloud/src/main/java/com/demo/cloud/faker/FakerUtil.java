package com.demo.cloud.faker;

import com.demo.cloud.model.DriveType;
import com.demo.cloud.model.VirtualMachine;
import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class FakerUtil {
    public static Iterator<int[]> getCategoryDataIterator(String catFile) throws IOException {
        InputStream stream = FakerUtil.class.getClassLoader().getResourceAsStream(catFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));

        ArrayList<int[]> cats = new ArrayList<>();
        String line = in.readLine();
        while ((line = in.readLine()) != null) {
            if (line.startsWith("END")) {
                break;
            }

            String[] split = line.split(",");
            cats.add(new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])});
        }

        in.close();
        return cats.iterator();
    }

    public static Iterator<String> getOrgImagesPathIterator(String orgImagesDir) {
        File imagesDir = new File(orgImagesDir);

        ArrayList<String> images = new ArrayList<>();
        for (File image : imagesDir.listFiles()) {
            images.add(image.getAbsolutePath());
        }

        return images.iterator();
    }

    public static LocalDateTime[][] generateTurnedOnAndOff(VirtualMachine machine, int activities) {
        int activityLength = 10;
        int activityPause = 2;

        LocalDateTime now = LocalDate.now().atStartOfDay().plusHours(8);
        LocalDateTime end = now.minusMonths(1);
        LocalDateTime start = end.minusDays((long) (activityLength + activityPause) * activities);

        ArrayList<LocalDateTime[]> dates = new ArrayList<>(activities);
        LocalDateTime turnedOn = start;
        for (int i = 0; i < activities; i++) {
            LocalDateTime turnedOff = turnedOn.plusDays(activityLength);
            dates.add(new LocalDateTime[]{turnedOn, turnedOff});

            turnedOn = turnedOff.plusDays(activityPause);
        }

        return dates.toArray(new LocalDateTime[activities][2]);
    }

    public static LocalDateTime generateTurnedOn(Faker faker) {
        return LocalDate.now().atStartOfDay().plusHours(8).minusMonths(1).plusDays(faker.number().numberBetween(2, 28 + 1));
    }

    private static Integer[] capacity = {256, 512, 1024, 2048, 5120};
    public static int getCapacity(Faker faker) {
        return faker.options().nextElement(capacity);
    }

    public static DriveType getType(Faker faker) {
        return faker.options().option(DriveType.class);
    }

    public static String generateLorem(Faker faker, int length) {
        return faker.lorem().fixedString(length);
    }

    public static String escapeApostrophe(String str) {
        return str.replaceAll("'", "''");
    }
}
