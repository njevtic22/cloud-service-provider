package com.demo.cloud.mapper;

import com.demo.cloud.dto.machines.MachineViewDto;
import com.demo.cloud.model.VirtualMachine;
import org.springframework.stereotype.Component;

@Component
public class VirtualMachineMapper {
    public MachineViewDto toViewDto(VirtualMachine machine) {
        return new MachineViewDto(
                machine.getId(),
                machine.getName(),
                machine.getOrganization().getId(),
                machine.getCategory().getId(),
                machine.getCategory().getName(),
                machine.getCategory().getCpu(),
                machine.getCategory().getRam(),
                machine.getCategory().getGpu()
        );
    }
}
