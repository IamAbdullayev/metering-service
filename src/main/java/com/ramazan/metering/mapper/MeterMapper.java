package com.ramazan.metering.mapper;

import com.ramazan.metering.controller.dto.MeterResponseDto;
import com.ramazan.metering.model.entity.Meter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeterMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "unit.symbol", target = "unit")
    MeterResponseDto toDto(Meter meter);
}
