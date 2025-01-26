package com.demo.cloud.util;

import com.demo.cloud.core.error.exceptions.BlankStringException;

import java.util.Objects;

public class Strings {
    public static String requireNonBlank(String str) {
        return requireNonBlank(str, "Passed string must not be blank.");
    }

    public static String requireNonBlank(String str, String message) {
        str = Objects.requireNonNull(str, message);
        if (str.isBlank()) {
            throw new BlankStringException(message);
        }
        return str;
    }
}

