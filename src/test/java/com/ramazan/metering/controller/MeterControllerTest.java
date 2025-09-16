package com.ramazan.metering.controller;

import com.ramazan.metering.controller.dto.MeterRequestDto;
import com.ramazan.metering.controller.dto.MeterResponseDto;
import com.ramazan.metering.model.enums.MeterType;
import com.ramazan.metering.model.enums.Unit;
import com.ramazan.metering.service.MeterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeterControllerTest {

    @InjectMocks
    private MeterController meterController;

    @Mock
    private MeterService meterService;

    @Test
    @DisplayName("Создание счётчика должно вернуть 200 и правильный объект")
    void createMeter_shouldReturnMeterResponse() {
        MeterRequestDto request = MeterRequestDto.builder()
                .type(MeterType.WATER)
                .unit(Unit.CUBIC_METER)
                .name("Kitchen Meter")
                .location("Kitchen")
                .build();

        MeterResponseDto response = MeterResponseDto.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .type(MeterType.WATER)
                .unit(Unit.CUBIC_METER.getSymbol())
                .createdAt(Instant.now())
                .name("Kitchen Meter")
                .location("Kitchen")
                .build();

        when(meterService.createMeter(any(MeterRequestDto.class), eq("user-123")))
                .thenReturn(response);

        ResponseEntity<MeterResponseDto> result = meterController.createMeter(request, "user-123");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getName()).isEqualTo("Kitchen Meter");
        assertThat(result.getBody().getLocation()).isEqualTo("Kitchen");

        verify(meterService).createMeter(any(MeterRequestDto.class), eq("user-123"));
    }

    @Test
    @DisplayName("Запрос всех счётчиков должен вернуть список")
    void getAllMeters_shouldReturnList() {
        MeterResponseDto meter1 = MeterResponseDto.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .type(MeterType.GAS)
                .unit(Unit.CUBIC_METER.getSymbol())
                .createdAt(Instant.now())
                .name("Gas Meter")
                .location("London")
                .build();

        MeterResponseDto meter2 = MeterResponseDto.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .type(MeterType.ELECTRICITY)
                .unit(Unit.KILOWATT_HOUR.getSymbol())
                .createdAt(Instant.now())
                .name("Electric Meter")
                .location("Baku")
                .build();

        when(meterService.getAllMeters("user-123")).thenReturn(List.of(meter1, meter2));

        ResponseEntity<List<MeterResponseDto>> result = meterController.getAllMeters("user-123");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getBody().get(0).getType()).isIn(MeterType.GAS, MeterType.ELECTRICITY);
        assertThat(result.getBody().get(1).getType()).isIn(MeterType.GAS, MeterType.ELECTRICITY);

        verify(meterService).getAllMeters("user-123");
    }

    @Test
    @DisplayName("Запрос счётчика по ID должен вернуть корректный объект")
    void getMeterById_shouldReturnOne() {
        UUID id = UUID.randomUUID();

        MeterResponseDto resp = MeterResponseDto.builder()
                .id(id)
                .userId(UUID.randomUUID())
                .type(MeterType.WATER)
                .unit(Unit.CUBIC_METER.getSymbol())
                .createdAt(Instant.now())
                .name("Hot Water Meter")
                .location("Bathroom")
                .build();

        when(meterService.getMeterById(eq(id), eq("user-123"))).thenReturn(resp);

        ResponseEntity<MeterResponseDto> result = meterController.getMeterById(id, "user-123");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getName()).isEqualTo("Hot Water Meter");
        assertThat(result.getBody().getLocation()).isEqualTo("Bathroom");

        verify(meterService).getMeterById(eq(id), eq("user-123"));
    }
}
