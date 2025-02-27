package com.example.monitorservice.controller;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    void createSensor_ValidRequest_ReturnsCreated() throws Exception {
        SensorRequest request = new SensorRequest();
        request.setName("Sensor1");
        request.setModel("Model1");
        request.setType("Pressure");

        SensorResponse response = new SensorResponse();
        when(sensorService.createSensor(any(SensorRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/sensors")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "VIEWER")
    void searchSensors_ValidRequest_ReturnsOk() throws Exception {
        when(sensorService.searchSensors(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/sensors")
                        .param("search", "Sensor1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    void updateSensor_ValidRequest_ReturnsOk() throws Exception {
        SensorRequest request = new SensorRequest();
        request.setName("Sensor1");
        request.setModel("Model1");
        request.setType("Pressure");

        SensorResponse response = new SensorResponse();
        when(sensorService.updateSensor(anyLong(), any(SensorRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/sensors/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    void deleteSensor_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/sensors/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
