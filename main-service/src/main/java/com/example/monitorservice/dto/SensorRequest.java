package com.example.monitorservice.dto;

import com.example.monitorservice.entity.Range;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest {
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String model;

    @NotNull
    private Range range;

    @NotBlank
    private String type;

    private String unit;

    @Size(max = 40)
    private String location;

    @Size(max = 200)
    private String description;
}
