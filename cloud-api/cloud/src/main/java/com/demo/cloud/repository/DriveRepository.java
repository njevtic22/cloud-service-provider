package com.demo.cloud.repository;

import com.demo.cloud.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriveRepository extends JpaRepository<Drive, Long> {
    Optional<Drive> findByIdAndArchivedFalse(Long id);
}
