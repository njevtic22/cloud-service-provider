package com.demo.cloud.repository;

import com.demo.cloud.model.VirtualMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long>, JpaSpecificationExecutor<VirtualMachine> {
    Optional<VirtualMachine> findByIdAndArchivedFalse(Long id);

    Optional<VirtualMachine> findByIdAndOrganizationIdAndArchivedFalse(Long id, Long orgId);

    boolean existsByName(String name);

    boolean existsByIdAndArchivedFalse(Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update VirtualMachine vm set vm.archived = true where vm.id = :id")
    int archiveById(Long id);
}
