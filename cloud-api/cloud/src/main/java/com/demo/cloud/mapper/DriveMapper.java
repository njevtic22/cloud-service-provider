package com.demo.cloud.mapper;

import com.demo.cloud.dto.drive.DriveViewDto;
import com.demo.cloud.model.Drive;
import org.springframework.stereotype.Component;

@Component
public class DriveMapper extends PageMapper<Drive, DriveViewDto> {
    @Override
    public DriveViewDto toDto(Drive model) {
        String machine = model.getMachine() == null ? null : model.getMachine().getName();
        return new DriveViewDto(
                model.getId(),
                model.getName(),
                model.getCapacity(),
                model.getType().toString(),
                model.getOrganization().getName(),
                machine
        );
    }
}
