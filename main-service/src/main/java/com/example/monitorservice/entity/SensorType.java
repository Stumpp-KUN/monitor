package com.example.monitorservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
// Предустановленные данные, берутся из БД.
public class SensorType {
    @Id
    private String name;
}
