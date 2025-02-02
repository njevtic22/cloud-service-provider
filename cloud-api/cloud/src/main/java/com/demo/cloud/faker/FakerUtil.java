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

    public static LocalDate[][] generateTurnedOnAndOff(VirtualMachine machine, int activities) {
        int activityLength = 10;
        int activityPause = 1;

        LocalDate now = LocalDate.now();
        LocalDate end = now.minusMonths(1);
        LocalDate start = end.minusDays((long) (activityLength + activityPause) * activities);

        ArrayList<LocalDate[]> dates = new ArrayList<>(activities);

        LocalDate turnedOn = start;
        for (int i = 0; i < activities; i++) {
            LocalDate turnedOff = turnedOn.plusDays(activityLength);
            dates.add(new LocalDate[]{turnedOn, turnedOff});

            turnedOn = turnedOff.plusDays(activityPause + 1);
        }

        return dates.toArray(new LocalDate[activities][2]);
    }

    public static LocalDate generateTurnedOn(Faker faker) {
        return LocalDate.now().minusDays(28 + faker.number().numberBetween(5, 26 + 1));
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
