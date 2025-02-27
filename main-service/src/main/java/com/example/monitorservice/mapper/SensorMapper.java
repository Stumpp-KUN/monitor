package com.example.monitorservice.mapper;

import com.example.monitorservice.dto.SensorRequest;
import com.example.monitorservice.dto.SensorResponse;
import com.example.monitorservice.entity.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SensorMapper {
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "unit", ignore = true)
    Sensor toEntity(SensorRequest request);

    @Mapping(target = "type", source = "type.name")
    @Mapping(target = "unit", source = "unit.name")
    SensorResponse toResponse(Sensor sensor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "unit", ignore = true)
    void updateEntity(SensorRequest request, @MappingTarget Sensor sensor);
}
