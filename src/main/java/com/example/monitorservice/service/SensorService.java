package com.example.monitorservice.service;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.entity.Sensor;
import com.example.monitorservice.entity.SensorType;
import com.example.monitorservice.entity.Unit;
import com.example.monitorservice.exception.SensorNotFoundException;
import com.example.monitorservice.mapper.SensorMapper;
import com.example.monitorservice.repository.SensorRepository;
import com.example.monitorservice.repository.SensorTypeRepository;
import com.example.monitorservice.repository.UnitRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final UnitRepository unitRepository;
    private final SensorMapper sensorMapper;

    public SensorResponse createSensor(SensorRequest request) {
        SensorType type = sensorTypeRepository.findById(request.getType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sensor type"));

        Unit unit = (request.getUnit() != null) ?
                unitRepository.findById(request.getUnit()).orElse(null) :
                null;

        Sensor sensor = sensorMapper.toEntity(request);
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor = sensorRepository.save(sensor);
        return sensorMapper.toResponse(sensor);
    }

    public List<SensorResponse> searchSensors(String searchTerm, Pageable pageable) {
        List<Sensor> sensors = sensorRepository.searchByNameOrModel(searchTerm, pageable);
        return sensors.stream()
                .map(sensorMapper::toResponse)
                .toList();
    }

    public SensorResponse getSensorById(Long id) {
        return sensorRepository.findById(id)
                .map(sensorMapper::toResponse)
                .orElseThrow(() -> new SensorNotFoundException(id));
    }

    public SensorResponse updateSensor(Long id, SensorRequest request) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id));

        SensorType type = sensorTypeRepository.findById(request.getType())
                .orElseThrow(() -> new ValidationException("Invalid sensor type"));

        Unit unit = (request.getUnit() != null) ?
                unitRepository.findById(request.getUnit()).orElse(null) :
                null;

        sensorMapper.updateEntity(request, sensor);
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor = sensorRepository.save(sensor);
        return sensorMapper.toResponse(sensor);
    }

    public void deleteSensor(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new SensorNotFoundException(id);
        }
        sensorRepository.deleteById(id);
    }
}
