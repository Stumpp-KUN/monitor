package com.example.monitorservice.controller;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/sensors")
@Tag(name = "Sensor Management", description = "API для управления сенсорами")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Operation(
            summary = "Создание нового сенсора",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Сенсор успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен")
            }
    )
    public ResponseEntity<SensorResponse> createSensor(@Parameter(description = "Данные для создания сенсора") @Valid
                                                           @RequestBody SensorRequest request) {
        return ResponseEntity.ok(sensorService.createSensor(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_VIEWER', 'ROLE_ADMINISTRATOR')")
    @Operation(
            summary = "Поиск сенсоров с пагинацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен")
            }
    )
    public ResponseEntity<?> searchSensors(
            @Parameter(description = "Поисковый запрос") @RequestParam(required = false) String search,
            @Parameter(description = "Параметры пагинации") Pageable pageable,
            @Parameter(description = "Дата начала периода") @RequestParam(required = false) LocalDateTime startDate
            ) {
        return ResponseEntity.ok(sensorService.searchSensors(search, pageable, startDate));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_VIEWER', 'ROLE_ADMINISTRATOR')")
    @Operation(
            summary = "Поиск сенсоров по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен")
            }
    )
    public ResponseEntity<SensorResponse> searchSensorsById(
            @Parameter(description = "Идентификатор сенсора") @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(sensorService.findSensorsById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Operation(
            summary = "Обновление сенсора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сенсор успешно обновлен"),
                    @ApiResponse(responseCode = "404", description = "Сенсор не найден"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
            }
    )
    public ResponseEntity<SensorResponse> updateSensor(
            @Parameter(description = "ID сенсора") @PathVariable Long id,
            @Valid @RequestBody SensorRequest request
    ) {
        return ResponseEntity.ok(sensorService.updateSensor(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Operation(
            summary = "Удаление сенсора",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сенсор успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Сенсор не найден")
            }
    )
    public ResponseEntity<Void> deleteSensor(@Parameter(description = "ID сенсора") @PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
