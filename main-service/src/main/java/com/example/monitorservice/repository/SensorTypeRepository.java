package com.example.monitorservice.repository;

import com.example.monitorservice.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorTypeRepository extends JpaRepository<SensorType, String> {}
