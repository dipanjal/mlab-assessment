package com.mlab.assessment.repository;

import com.mlab.assessment.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findDistinctByUsername(String username);
}
