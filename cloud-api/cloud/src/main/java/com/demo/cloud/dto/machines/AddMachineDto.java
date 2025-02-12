package com.demo.cloud.dto.machines;

public class AddMachineDto extends RequestMachineDto {
    public AddMachineDto(String name, Long organizationId, Long categoryId) {
        super(name, organizationId, categoryId);
    }
}
