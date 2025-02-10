package com.demo.cloud.controller;


import com.demo.cloud.dto.machines.MachineViewDto;
import com.demo.cloud.mapper.VirtualMachineMapper;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.service.VirtualMachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/virtual-machines")
public class VirtualMachineController {
    private final VirtualMachineService service;
    private final VirtualMachineMapper mapper;

    public VirtualMachineController(VirtualMachineService service, VirtualMachineMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<MachineViewDto> getById(@PathVariable Long id) {
        VirtualMachine found = service.getById(id);
        MachineViewDto foundDto = mapper.toViewDto(found);
        return ResponseEntity.ok(foundDto);
    }
}
