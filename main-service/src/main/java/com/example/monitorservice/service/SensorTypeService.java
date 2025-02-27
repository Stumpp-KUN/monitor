package com.example.monitorservice.service;

import com.example.monitorservice.entity.SensorType;
import com.example.monitorservice.repository.SensorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorTypeService {
    private final SensorTypeRepository sensorTypeRepository;

    public Optional<SensorType> findByName(String code) {
        return sensorTypeRepository.findById(code);
    }
}
