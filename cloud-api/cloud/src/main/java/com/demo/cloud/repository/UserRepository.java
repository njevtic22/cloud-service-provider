package com.demo.cloud.repository;

import com.demo.cloud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByIdAndArchivedFalse(Long id);

    Optional<User> findByUsernameAndArchivedFalse(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByIdAndArchivedFalse(Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User u set u.password = :newPassword where u.id = :id")
    int updatePasswordById(Long id, String newPassword);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User u set u.archived = true where u.id = :id")
    int archiveById(Long id);
}
