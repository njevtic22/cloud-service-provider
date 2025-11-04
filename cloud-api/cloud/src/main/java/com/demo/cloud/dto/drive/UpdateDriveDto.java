package com.demo.cloud.dto.drive;

public class UpdateDriveDto extends RequestDriveDto {
    public UpdateDriveDto(String name, int capacity, String type) {
        super(name, capacity, type);
    }
}
