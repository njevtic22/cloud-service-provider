package com.demo.cloud.service;

import com.demo.cloud.model.VirtualMachine;

public interface VirtualMachineService extends EntityGetter<Long, VirtualMachine>, EntityDeleter<Long> {
    VirtualMachine add(String name, Long organizationId, Long categoryId);

    VirtualMachine update(Long id, String name, Long categoryId);
}
