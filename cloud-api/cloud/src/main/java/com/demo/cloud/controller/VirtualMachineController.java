package com.demo.cloud.controller;


import com.demo.cloud.core.PaginatedResponse;
import com.demo.cloud.core.aspect.annotation.MachinePageable;
import com.demo.cloud.dto.machine.AddMachineDto;
import com.demo.cloud.dto.machine.MachineViewDto;
import com.demo.cloud.dto.machine.UpdateMachineDto;
import com.demo.cloud.filterParams.MachineFilter;
import com.demo.cloud.mapper.VirtualMachineMapper;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.service.VirtualMachineService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/virtual-machines")
public class VirtualMachineController {
    private final VirtualMachineService service;
    private final VirtualMachineMapper mapper;

    public VirtualMachineController(VirtualMachineService service, VirtualMachineMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> add(@Valid @RequestBody AddMachineDto newDto, UriComponentsBuilder uriBuilder) {
        VirtualMachine added = service.add(newDto.getName(), newDto.getOrganizationId(), newDto.getCategoryId());

        uriBuilder.path("api/virtual-machines/{id}");
        URI uri = uriBuilder.buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<MachineViewDto>> getAll(
            @MachinePageable @PageableDefault(sort="id") Pageable pageable,
            MachineFilter filter
    ) {
        Page<VirtualMachine> machines = service.getAll(pageable, filter.getParams());
        return ResponseEntity.ok(mapper.toDto(machines));
    }

    @GetMapping("{id}")
    public ResponseEntity<MachineViewDto> getById(@PathVariable Long id) {
        VirtualMachine found = service.getById(id);
        MachineViewDto foundDto = mapper.toDto(found);
        return ResponseEntity.ok(foundDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<MachineViewDto> update(@PathVariable Long id, @Valid @RequestBody UpdateMachineDto changesDto) {
        VirtualMachine updated = service.update(id, changesDto.getName(), changesDto.getCategoryId());
        MachineViewDto updatedDto = mapper.toDto(updated);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("{id}/toggle")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<MachineViewDto> update(@PathVariable Long id) {
        VirtualMachine toggled = service.toggle(id);
        MachineViewDto updatedDto = mapper.toDto(toggled);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
