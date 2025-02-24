package com.demo.cloud.dto.drive;

public class AddDriveDto extends RequestDriveDto{
    public AddDriveDto(String name, int capacity, String type, Long orgId) {
        super(name, capacity, type, orgId);
    }
}
