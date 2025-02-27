package com.example.statictics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private int totalSensors;

    @ElementCollection
    @CollectionTable(
            name = "statistic_type_counts",
            joinColumns = @JoinColumn(name = "statistic_id")
    )
    @MapKeyColumn(name = "type_counts_key")
    @Column(name = "type_counts")
    private Map<String, Integer> typeCounts;
}
