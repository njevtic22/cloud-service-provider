package com.demo.cloud.dto.machine;

public class UpdateMachineDto extends RequestMachineDto {
    public UpdateMachineDto(String name, Long categoryId) {
        super(name, categoryId);
    }
}
