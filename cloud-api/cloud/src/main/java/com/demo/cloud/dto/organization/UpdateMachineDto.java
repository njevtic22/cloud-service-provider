package com.demo.cloud.dto.organization;

import com.demo.cloud.dto.machines.RequestMachineDto;

public class UpdateMachineDto extends RequestMachineDto {
    public UpdateMachineDto(String name, Long categoryId) {
        super(name, categoryId);
    }
}
