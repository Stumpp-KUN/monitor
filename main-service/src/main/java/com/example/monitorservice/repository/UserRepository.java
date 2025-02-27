package com.example.monitorservice.repository;

import com.example.monitorservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT a FROM User a where a.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
