package com.example.statictics.model;

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
