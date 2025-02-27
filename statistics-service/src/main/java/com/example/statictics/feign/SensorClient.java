package com.example.statictics.feign;

import com.example.statictics.config.FeignConfig;
import com.example.statictics.model.SensorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "monitor-sensors", url = "http://localhost:8080", configuration = FeignConfig.class)
public interface SensorClient {
    @GetMapping("/api/v1/sensors")
    Page<SensorResponse> getAllSensors(@RequestParam(required = false)LocalDateTime startDate, Pageable pageable);
}
