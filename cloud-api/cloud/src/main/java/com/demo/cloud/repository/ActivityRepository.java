package com.demo.cloud.repository;

import com.demo.cloud.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {
    Optional<Activity> findByMachineIdAndTurnedOffNull(Long machineId);

    @Query("select sum(a.profit) from Activity a where a.machine.id = :machineId")
    long calcProfit(Long machineId);
}
