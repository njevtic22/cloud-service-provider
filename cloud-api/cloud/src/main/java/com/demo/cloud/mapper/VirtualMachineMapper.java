package com.demo.cloud.mapper;

import com.demo.cloud.dto.machine.MachineViewDto;
import com.demo.cloud.model.VirtualMachine;
import org.springframework.stereotype.Component;

@Component
public class VirtualMachineMapper extends PageMapper<VirtualMachine, MachineViewDto> {
    @Override
    public MachineViewDto toDto(VirtualMachine machine) {
        return new MachineViewDto(
                machine.getId(),
                machine.getName(),
                machine.isActive(),
                machine.getOrganization().getName(),
                machine.getCategory().getName(),
                machine.getCategory().getCpu(),
                machine.getCategory().getRam(),
                machine.getCategory().getGpu()
        );
    }
}
