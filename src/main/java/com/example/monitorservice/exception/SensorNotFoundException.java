package com.example.monitorservice.exception;

import org.springframework.http.HttpStatus;

public class SensorNotFoundException extends CustomException {
    public SensorNotFoundException(Long id) {
        super("Sensor with id " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
