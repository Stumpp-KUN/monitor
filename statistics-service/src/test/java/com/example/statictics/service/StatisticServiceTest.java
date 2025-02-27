package com.example.statictics.service;

import com.example.statictics.feign.SensorClient;
import com.example.statictics.model.SensorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticServiceTest {

    @MockitoBean
    private SensorClient sensorClient;

    @Autowired
    private StatisticService statisticService;

//    @Test
//    void collectStatistics_ValidData_SavesStatistic() {
//        SensorResponse sensor1 = new SensorResponse();
//        sensor1.setType("Pressure");
//
//        SensorResponse sensor2 = new SensorResponse();
//        sensor2.setType("Temperature");
//
//        when(sensorClient.getAllSensors().getContent()).thenReturn(List.of(sensor1, sensor2));
//
//        statisticService.collectStatistics();
//
//        verify(sensorClient, times(1)).getAllSensors();
//    }
}
