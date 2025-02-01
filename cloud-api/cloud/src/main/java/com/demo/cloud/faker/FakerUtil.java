package com.demo.cloud.faker;

import com.github.javafaker.Faker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class FakerUtil {
    public static Iterator<String> getOrgImagesPathIterator(String orgImagesDir) {
        File imagesDir = new File(orgImagesDir);

        ArrayList<String> images = new ArrayList<>();
        for (File image : imagesDir.listFiles()) {
            images.add(image.getAbsolutePath());
        }

        return images.iterator();
    }

    public static String generateLorem(Faker faker, int length) {
        return faker.lorem().fixedString(length);
    }

    public static String escapeApostrophe(String str) {
        return str.replaceAll("'", "''");
    }
}
