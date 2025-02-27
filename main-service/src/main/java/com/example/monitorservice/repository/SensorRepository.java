package com.example.monitorservice.repository;

import com.example.monitorservice.entity.Sensor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    @Query("SELECT s FROM Sensor s WHERE " +
            "LOWER(s.name) LIKE LOWER(concat('%', :search, '%')) OR " +
            "LOWER(s.model) LIKE LOWER(concat('%', :search, '%'))")
    List<Sensor> searchByNameOrModel(@Param("search") String search, Pageable pageable);

    @Query("SELECT s FROM Sensor s WHERE s.createdTime > :createdTime")
    List<Sensor> findByCreatedTimeAfter(@Param("createdTime") LocalDateTime createdTime);
}
