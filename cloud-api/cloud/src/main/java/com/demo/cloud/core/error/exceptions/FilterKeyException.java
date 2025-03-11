package com.demo.cloud.core.error.exceptions;

public class FilterKeyException extends IllegalArgumentException {
    public FilterKeyException(String key) {
        super("Invalid filter key " + key);
    }
}
