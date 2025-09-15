package com.ramazan.metering.service;

import com.ramazan.metering.controller.dto.MeterRequestDto;
import com.ramazan.metering.controller.dto.MeterResponseDto;
import com.ramazan.metering.exception.ForbiddenException;
import com.ramazan.metering.exception.NotFoundException;
import com.ramazan.metering.mapper.MeterMapper;
import com.ramazan.metering.model.entity.Meter;
import com.ramazan.metering.model.entity.User;
import com.ramazan.metering.repository.MeterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeterService {

    private final MeterRepository meterRepository;
    private final MeterMapper mapper;
    private final UserService userService;

    public MeterResponseDto createMeter(MeterRequestDto requestDto, String sub) {
        User user = userService.getOrCreateUser(sub);
        log.debug("Resolved user with id={} for sub={}", user.getId(), sub);

        Meter meter = Meter.builder()
                .user(user)
                .type(requestDto.getType())
                .unit(requestDto.getUnit())
                .name(requestDto.getName())
                .location(requestDto.getLocation())
                .build();
        Meter saved = meterRepository.save(meter);
        log.info("Meter created with id={} for user sub={}", saved.getId(), sub);

        return mapper.toDto(saved);
    }

    public List<MeterResponseDto> getAllMeters(String sub) {
        User user = userService.getBySub(sub);
        List<Meter> meters = meterRepository.findAllByUser(user);
        log.info("Fetched {} meters for user sub={}", meters.size(), sub);

        return meters.stream()
                .map(mapper::toDto)
                .toList();
    }

    public MeterResponseDto getMeterById(UUID id, String sub) {
        User user = userService.getBySub(sub);

        Meter meter = meterRepository.findMeterById(id)
                .orElseThrow(() -> new NotFoundException("Meter not found: " + id));

        if (!meter.getUser().getId().equals(user.getId())) {
            log.warn("Unauthorized access attempt: userId={} tried to access meterId={}", user.getId(), meter.getId());
            throw new ForbiddenException("You do not have access to this meter reading");
        }

        log.info("Fetched meter id={} for user sub={}", id, sub);

        return mapper.toDto(meter);
    }

    public Meter getMeter(UUID id) {
        Meter meter = meterRepository.findMeterById(id)
                .orElseThrow(() -> {
                    log.warn("Meter not found with id={}", id);
                    return new NotFoundException("Meter not found: " + id);
                });
        log.debug("Found meter id={} type={} unit={}", meter.getId(), meter.getType(), meter.getUnit());

        return meter;
    }

    // ========================
    // TODO: implement update
    // ========================
    public MeterResponseDto updateMeter(UUID id, MeterRequestDto requestDto, String sub) {
        // TODO: load meter by id
        // TODO: check that it belongs to the user (sub)
        // TODO: update fields (type, unit, name, location)
        // TODO: save and return updated meter
        throw new UnsupportedOperationException("Update meter not implemented yet");
    }

    // ========================
    // TODO: implement delete
    // ========================
    public void deleteMeter(UUID id, String sub) {
        // TODO: load meter by id
        // TODO: check that it belongs to the user (sub)
        // TODO: delete meter
        throw new UnsupportedOperationException("Delete meter not implemented yet");
    }
}
