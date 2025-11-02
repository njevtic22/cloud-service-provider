package com.demo.cloud.dto.organization;

import com.demo.cloud.dto.image.ImageViewDto;

public class SingleOrganizationDto extends OrganizationViewDto {
    private final long users;
    private final long machines;
    private final long drivers;

    public SingleOrganizationDto(Long id, String name, String description, ImageViewDto logo, long users, long machines, long drivers) {
        super(id, name, description, logo);
        this.users = users;
        this.machines = machines;
        this.drivers = drivers;
    }

    public long getUsers() {
        return users;
    }

    public long getMachines() {
        return machines;
    }

    public long getDrivers() {
        return drivers;
    }
}
