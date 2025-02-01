package com.demo.cloud.core.error;

public class ApiError {
    private final long timestamp = System.currentTimeMillis();
    private final String message;

    public ApiError() {
        this.message = "Unknown error.";
    }

    public ApiError(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
