package com.example.statictics.service;

import com.example.statictics.entity.Statistic;
import com.example.statictics.feign.SensorClient;
import com.example.statictics.model.SensorResponse;
import com.example.statictics.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final SensorClient sensorClient;
    private final StatisticRepository statisticRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void collectStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.withHour(2).withMinute(0).withSecond(0).minusDays(1);

        List<SensorResponse> sensors = sensorClient.getAllSensors(startTime, Pageable.unpaged()).getContent();

        if (!sensors.isEmpty()) {
            Statistic statistic = new Statistic();
            statistic.setDate(LocalDate.now());
            statistic.setTotalSensors(sensors.size());
            Map<String, Integer> typeCounts = sensors.stream()
                    .collect(Collectors.groupingBy(
                            SensorResponse::getType,
                            Collectors.summingInt(e -> 1)
                    ));

            statistic.setTypeCounts(typeCounts);

            statisticRepository.save(statistic);
        }
    }

    @Transactional(readOnly = true)
    public List<Statistic> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate) {
        return statisticRepository.findByDateBetween(startDate, endDate);
    }
}
