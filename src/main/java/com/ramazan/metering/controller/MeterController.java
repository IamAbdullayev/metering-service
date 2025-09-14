package com.ramazan.metering.controller;

import com.ramazan.metering.controller.dto.MeterRequestDto;
import com.ramazan.metering.controller.dto.MeterResponseDto;
import com.ramazan.metering.service.MeterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meters")
@RequiredArgsConstructor
@Tag(name = "Meter API", description = "Operations with meters (create, get, list)")
public class MeterController {

    private final MeterService meterService;

    @PostMapping
    public ResponseEntity<MeterResponseDto> createMeter(@Valid @RequestBody MeterRequestDto requestDto) {
        return null;
    }
}
