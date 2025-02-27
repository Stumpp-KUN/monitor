package com.example.monitorservice.service;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.entity.Sensor;
import com.example.monitorservice.entity.SensorType;
import com.example.monitorservice.entity.Unit;
import com.example.monitorservice.exception.ResourceNotFoundException;
import com.example.monitorservice.mapper.SensorMapper;
import com.example.monitorservice.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final SensorTypeService sensorTypeService;
    private final UnitService unitService;
    private final SensorMapper sensorMapper;

    @Transactional
    public SensorResponse createSensor(SensorRequest request) {
        validateSensorRequest(request);

        Sensor sensor = sensorMapper.toEntity(request);
        sensor.setType(getSensorType(request.getType()));
        sensor.setUnit(getUnit(request.getUnit()));

        Sensor savedSensor = sensorRepository.save(sensor);
        log.info("Created sensor with ID: {}", savedSensor.getId());

        return sensorMapper.toResponse(savedSensor);
    }

    @Transactional(readOnly = true)
    public Page<SensorResponse> searchSensors(String searchTerm, Pageable pageable, LocalDateTime startDate) {
        if (startDate != null) {
            var sensors = sensorRepository.findByCreatedTimeAfter(startDate).stream().map(sensorMapper::toResponse).toList();
            return new PageImpl<>(sensors, pageable, sensors.size());
        }
        if (StringUtils.hasText(searchTerm)) {
            log.debug("Searching sensors with term: '{}'", searchTerm);
            List<Sensor> sensors = sensorRepository.searchByNameOrModel(searchTerm, pageable);
            List<SensorResponse> sensorResponses = sensors.stream()
                    .map(sensorMapper::toResponse)
                    .collect(Collectors.toList());
            return new PageImpl<>(sensorResponses, pageable, sensors.size());
        }
        return sensorRepository.findAll(pageable)
                .map(sensorMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public SensorResponse findSensorsById(Long id) {
        return sensorMapper.toResponse(sensorRepository.findById(id).orElse(null));
    }

    @Transactional
    public SensorResponse updateSensor(Long id, SensorRequest request) {
        validateSensorRequest(request);

        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor", "id", id));

        sensorMapper.updateEntity(request, sensor);
        sensor.setType(getSensorType(request.getType()));
        sensor.setUnit(getUnit(request.getUnit()));

        Sensor updatedSensor = sensorRepository.save(sensor);
        log.info("Updated sensor with ID: {}", id);

        return sensorMapper.toResponse(updatedSensor);
    }

    @Transactional
    public void deleteSensor(Long id) {
        if (sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
            log.info("Deleted sensor with ID: {}", id);
        } else {
            log.warn("Attempt to delete non-existing sensor with ID: {}", id);
            throw new ResourceNotFoundException("Sensor", "id", id);
        }
    }

    private SensorType getSensorType(String typeCode) {
        return sensorTypeService.findByName(typeCode)
                .orElseThrow(() -> {
                    log.error("Invalid sensor type code: {}", typeCode);
                    return new ResourceNotFoundException("SensorType", "code", typeCode);
                });
    }

    private Unit getUnit(String unitCode) {
        if (unitCode == null) return null;
        return unitService.findByName(unitCode)
                .orElseThrow(() -> {
                    log.error("Invalid unit code: {}", unitCode);
                    return new ResourceNotFoundException("Unit", "code", unitCode);
                });
    }

    private void validateSensorRequest(SensorRequest request) {
        if (request == null) {
            log.error("Sensor request cannot be null");
            throw new IllegalArgumentException("Sensor request is null");
        }
    }
}