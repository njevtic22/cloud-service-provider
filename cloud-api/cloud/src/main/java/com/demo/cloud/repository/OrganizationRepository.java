package com.demo.cloud.repository;

import com.demo.cloud.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    Optional<Organization> findByIdAndArchivedFalse(Long id);

    boolean existsByName(String name);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Organization o set o.logo = :logo where o.id = :id and o.archived = false")
    int updateLogo(Long id, String logo);
}
