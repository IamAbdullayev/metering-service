package com.ramazan.metering.controller.dto;

import com.ramazan.metering.model.enums.MeterType;
import com.ramazan.metering.model.enums.Unit;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeterRequestDto {
    @NotNull(message = "Type is required")
    private MeterType type;
    @NotNull(message = "Unit is required")
    private Unit unit;
    private String name;
    private String location;
}
