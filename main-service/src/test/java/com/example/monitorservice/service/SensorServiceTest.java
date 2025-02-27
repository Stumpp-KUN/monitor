package com.example.monitorservice.service;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.entity.Sensor;
import com.example.monitorservice.entity.SensorType;
import com.example.monitorservice.entity.Unit;
import com.example.monitorservice.exception.ResourceNotFoundException;
import com.example.monitorservice.mapper.SensorMapper;
import com.example.monitorservice.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorTypeService sensorTypeService;

    @Mock
    private UnitService unitService;

    @Mock
    private SensorMapper sensorMapper;

    @InjectMocks
    private SensorService sensorService;

    @Test
    void createSensor_ValidRequest_ReturnsSensorResponse() {
        SensorRequest request = new SensorRequest();
        request.setName("Sensor1");
        request.setModel("Model1");
        request.setType("Pressure");
        request.setUnit("bar");

        Sensor sensor = new Sensor();
        SensorResponse response = new SensorResponse();

        when(sensorMapper.toEntity(request)).thenReturn(sensor);
        when(sensorTypeService.findByName("Pressure")).thenReturn(Optional.of(new SensorType()));
        when(unitService.findByName("bar")).thenReturn(Optional.of(new Unit()));
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toResponse(sensor)).thenReturn(response);

        SensorResponse result = sensorService.createSensor(request);

        assertNotNull(result);
        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    void createSensor_InvalidType_ThrowsResourceNotFoundException() {
        SensorRequest request = new SensorRequest();
        request.setType("InvalidType");

        when(sensorTypeService.findByName("InvalidType")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sensorService.createSensor(request));
    }

    @Test
    void searchSensors_WithSearchTerm_ReturnsFilteredResults() {
        String searchTerm = "Sensor1";
        Pageable pageable = Pageable.unpaged();
        Sensor sensor = new Sensor();
        SensorResponse response = new SensorResponse();

        when(sensorRepository.searchByNameOrModel(searchTerm, pageable)).thenReturn(Collections.singletonList(sensor));
        when(sensorMapper.toResponse(sensor)).thenReturn(response);

        Page<SensorResponse> result = sensorService.searchSensors(searchTerm, pageable);

        assertEquals(1, result.getContent().size());
        verify(sensorRepository, times(1)).searchByNameOrModel(searchTerm, pageable);
    }

    @Test
    void updateSensor_ValidRequest_ReturnsUpdatedSensorResponse() {
        Long id = 1L;
        SensorRequest request = new SensorRequest();
        request.setType("Pressure");
        request.setUnit("bar");

        Sensor sensor = new Sensor();
        SensorResponse response = new SensorResponse();

        when(sensorRepository.findById(id)).thenReturn(Optional.of(sensor));
        when(sensorTypeService.findByName("Pressure")).thenReturn(Optional.of(new SensorType()));
        when(unitService.findByName("bar")).thenReturn(Optional.of(new Unit()));
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toResponse(sensor)).thenReturn(response);

        SensorResponse result = sensorService.updateSensor(id, request);

        assertNotNull(result);
        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    void deleteSensor_ExistingId_DeletesSensor() {
        Long id = 1L;
        when(sensorRepository.existsById(id)).thenReturn(true);

        sensorService.deleteSensor(id);

        verify(sensorRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSensor_NonExistingId_ThrowsResourceNotFoundException() {
        Long id = 1L;
        when(sensorRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> sensorService.deleteSensor(id));
    }
}