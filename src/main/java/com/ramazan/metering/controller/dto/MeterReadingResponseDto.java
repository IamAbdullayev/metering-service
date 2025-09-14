package com.ramazan.metering.controller.dto;

import com.ramazan.metering.model.enums.MeterType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class MeterReadingResponseDto {
    private UUID id;
    private UUID meterId;
    private UUID userId;
    private MeterType meterType;
    private String unit;
    private BigDecimal value;
    private LocalDate readingDate;
    private Instant createdAt;
}
