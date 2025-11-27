package com.demo.cloud.repository;

import com.demo.cloud.model.Drive;
import com.demo.cloud.model.DriveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriveRepository extends JpaRepository<Drive, Long>, JpaSpecificationExecutor<Drive> {
    Optional<Drive> findByIdAndArchivedFalse(Long id);

    boolean existsByName(String name);

    boolean existsByIdAndArchivedFalse(Long id);

    boolean existsByIdAndOrganizationIdAndArchivedFalse(Long driveId, Long orgId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Drive d set d.archived = true where d.id = :id")
    int archiveById(Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Drive d set d.machine = null where d.machine.id = :machineId")
    int detachAll(Long machineId);

    // variables not allowed in select clause using hibernate
//    @Query("select coalesce(sum(d.capacity) * :multiplier, 0) from Drive d where d.archived = false and d.machine.id = :machineId and d.type = :type")
//    float calcProfit(Long machineId, DriveType type, Float multiplier);

    @Query("select coalesce(sum(d.capacity), 0) from Drive d where d.archived = false and d.machine.id = :machineId and d.type = :type")
    float sumCapacity(Long machineId, DriveType type);
}
