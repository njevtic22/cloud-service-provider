package com.demo.cloud.repository;

import com.demo.cloud.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DriveRepository extends JpaRepository<Drive, Long>, JpaSpecificationExecutor<Drive> {
    Optional<Drive> findByIdAndArchivedFalse(Long id);

    boolean existsByName(String name);
}
