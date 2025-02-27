package com.example.statictics.repository;

import com.example.statictics.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
