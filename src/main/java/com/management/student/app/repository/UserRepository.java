package com.management.student.app.repository;

import com.management.student.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String username);
    boolean existsByEmailAddress(String email);

}
