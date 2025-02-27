package com.example.monitorservice.service;

import com.example.monitorservice.entity.Unit;
import com.example.monitorservice.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Optional<Unit> findByName(String code) {
        return unitRepository.findById(code);
    }
}
