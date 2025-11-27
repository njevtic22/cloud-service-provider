package com.demo.cloud.model;

public enum DriveType {
    HDD(0.1f),
    SSD(0.3f);

    private final float multiplier;

    DriveType(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }
}
