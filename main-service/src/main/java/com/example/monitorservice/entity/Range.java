package com.example.monitorservice.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Range {
    @Positive(message = "'from' must be positive")
    private Integer rangeFrom;

    @Positive(message = "'to' must be positive")
    private Integer rangeTo;

    @AssertTrue(message = "'from' must be less than 'to'")
    public boolean isValidRange() {
        return rangeFrom < rangeTo;
    }
}
