package com.demo.cloud.repository;

import com.demo.cloud.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findAllByName(Pageable pageable, String name);

    Optional<Role> findByName(String name);
}
