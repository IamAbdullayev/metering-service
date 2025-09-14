package com.ramazan.metering.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class MeterReadingRequestDto {
    @NotNull(message = "Meter id should not be null")
    private UUID meterId;
    @NotNull(message = "Value should not be null")
    private BigDecimal value;
    @NotNull(message = "Reading date should not be null")
    private LocalDate readingDate;
}
