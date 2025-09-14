package com.ramazan.metering.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Unit {
    CUBIC_METER("m3"),
    KILOWATT_HOUR("kWh"),
    MEGAWATT_HOUR("MWh"),
    UNKNOWN("unit");

    private final String symbol;
}
