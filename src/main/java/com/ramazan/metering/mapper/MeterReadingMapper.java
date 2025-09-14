package com.ramazan.metering.mapper;

import com.ramazan.metering.controller.dto.MeterReadingResponseDto;
import com.ramazan.metering.model.entity.MeterReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeterReadingMapper {

    @Mapping(source = "meter.id", target = "meterId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "meter.type", target = "meterType")
    @Mapping(source = "meter.unit.symbol", target = "unit")
    MeterReadingResponseDto toDto(MeterReading meterReading);
}
