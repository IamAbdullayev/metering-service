package com.ramazan.metering.controller.dto;

import com.ramazan.metering.model.enums.MeterType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class MeterResponseDto {
    private UUID id;
    private UUID userId;
    private MeterType type;
    private String unit;
    private Instant createdAt;
    private String name;
    private String location;
}
