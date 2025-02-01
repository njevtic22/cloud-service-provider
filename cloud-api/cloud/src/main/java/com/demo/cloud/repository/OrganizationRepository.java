package com.demo.cloud.repository;

import com.demo.cloud.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByIdAndArchivedFalse(Long id);
}
