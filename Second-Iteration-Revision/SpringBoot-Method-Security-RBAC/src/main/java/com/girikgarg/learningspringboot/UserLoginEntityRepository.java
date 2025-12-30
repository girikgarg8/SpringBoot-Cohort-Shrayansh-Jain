package com.girikgarg.learningspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserLoginEntityRepository extends JpaRepository<UserLoginEntity, Long> {
    Optional<UserLoginEntity> findByUsername(String username);
}

