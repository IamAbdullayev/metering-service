package com.ramazan.metering.service;

import com.ramazan.metering.controller.dto.MeterReadingRequestDto;
import com.ramazan.metering.controller.dto.MeterReadingResponseDto;
import com.ramazan.metering.exception.ForbiddenException;
import com.ramazan.metering.exception.NotFoundException;
import com.ramazan.metering.mapper.MeterReadingMapper;
import com.ramazan.metering.model.entity.Meter;
import com.ramazan.metering.model.entity.MeterReading;
import com.ramazan.metering.model.entity.User;
import com.ramazan.metering.repository.MeterReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;
    private final MeterReadingMapper meterReadingMapper;
    private final UserService userService;
    private final MeterService meterService;

    public MeterReadingResponseDto createMeterReading(MeterReadingRequestDto requestDto, String sub) {
        log.info("Creating meter reading for user sub={} and meterId={}", sub, requestDto.getMeterId());

        User user = userService.getBySub(sub);
        Meter meter = meterService.getMeter(requestDto.getMeterId());

        if (!user.getId().equals(meter.getUser().getId())) {
            log.warn("Unauthorized access attempt: userId={} tried to access meterId={}", user.getId(), meter.getId());
            throw new ForbiddenException("You do not have access to this meter reading");
        }

        MeterReading meterReading = MeterReading.builder()
                .meter(meter)
                .user(user)
                .value(requestDto.getValue())
                .readingDate(requestDto.getReadingDate())
                .build();
        MeterReading saved = meterReadingRepository.save(meterReading);
        log.info("Meter reading created with id={} for meterId={} by userId={}", saved.getId(), meter.getId(), user.getId());

        return meterReadingMapper.toDto(saved);
    }

    public List<MeterReadingResponseDto> getAllReadings(String sub) {
        log.info("Fetching all meter readings for user sub={}", sub);

        User user = userService.getBySub(sub);

        List<MeterReading> readings = meterReadingRepository.findAllByUser(user);
        log.debug("Found {} readings for userId={}", readings.size(), user.getId());

        return readings.stream()
                .map(meterReadingMapper::toDto)
                .toList();
    }

    public MeterReadingResponseDto getReadingById(UUID readingId, String sub) {
        log.info("Fetching meter reading id={} for user sub={}", readingId, sub);

        User user = userService.getBySub(sub);

        MeterReading reading = meterReadingRepository.findById(readingId)
                .orElseThrow(() -> new NotFoundException("Meter reading not found: " + readingId));

        if (!reading.getUser().getId().equals(user.getId())) {
            log.warn("Unauthorized access attempt: userId={} tried to access readingId={}", user.getId(), reading.getId());
            throw new ForbiddenException("You do not have access to this meter reading");
        }
        log.info("Found meter reading id={} for userId={}", reading.getId(), user.getId());

        return meterReadingMapper.toDto(reading);
    }
}
