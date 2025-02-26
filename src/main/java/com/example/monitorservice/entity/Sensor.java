package com.example.monitorservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 30, message = "Name must be 3-30 characters")
    private String name;

    @NotBlank(message = "Model is mandatory")
    @Size(max = 15, message = "Model must be up to 15 characters")
    private String model;

    @Embedded
    private Range range;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private SensorType type;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Size(max = 40, message = "Location must be up to 40 characters")
    private String location;

    @Size(max = 200, message = "Description must be up to 200 characters")
    private String description;
}
