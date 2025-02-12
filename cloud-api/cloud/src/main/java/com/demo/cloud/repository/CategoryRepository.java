package com.demo.cloud.repository;

import com.demo.cloud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndArchivedFalse(Long id);
}
