package com.example.monitorservice.controller;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.service.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SensorResponse> createSensor(@Valid @RequestBody SensorRequest request) {
        return ResponseEntity.ok(sensorService.createSensor(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER', 'ADMINISTRATOR')")
    public ResponseEntity<List<SensorResponse>> searchSensors(
            @RequestParam(required = false) String search, Pageable pageable
    ) {
        return ResponseEntity.ok(sensorService.searchSensors(search, pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SensorResponse> updateSensor(
            @PathVariable Long id,
            @Valid @RequestBody SensorRequest request
    ) {
        return ResponseEntity.ok(sensorService.updateSensor(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
