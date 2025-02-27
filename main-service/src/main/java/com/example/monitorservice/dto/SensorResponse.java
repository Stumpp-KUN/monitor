package com.example.monitorservice.dto;

import com.example.monitorservice.entity.Range;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorResponse {
    private Long id;
    private String name;
    private String model;
    private Range range;
    private String type;
    private String unit;
    private String location;
    private String description;
}
