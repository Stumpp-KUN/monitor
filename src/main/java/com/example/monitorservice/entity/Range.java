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
    private Integer from;

    @Positive(message = "'to' must be positive")
    private Integer to;

    @AssertTrue(message = "'from' must be less than 'to'")
    public boolean isValidRange() {
        return from < to;
    }
}
