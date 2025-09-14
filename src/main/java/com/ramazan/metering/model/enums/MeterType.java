package com.ramazan.metering.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum MeterType {
    WATER(Set.of(Unit.CUBIC_METER)),
    ELECTRICITY(Set.of(Unit.KILOWATT_HOUR, Unit.MEGAWATT_HOUR)),
    GAS(Set.of(Unit.CUBIC_METER));

    private final Set<Unit> supportedUnits;
}
