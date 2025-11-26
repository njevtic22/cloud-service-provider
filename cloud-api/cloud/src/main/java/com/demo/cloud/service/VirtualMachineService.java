package com.demo.cloud.service;

import com.demo.cloud.model.VirtualMachine;

import java.util.Map;

public interface VirtualMachineService extends EntityGetter<Long, VirtualMachine>, EntityDeleter<Long> {
    VirtualMachine add(String name, Long organizationId, Long categoryId);

    VirtualMachine update(Long id, String name, Long categoryId);

    VirtualMachine toggle(Long id);

    long count(Map<String, String> filter);
}
