package com.ramazan.metering.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto {
    private UUID id;
    private String sub;
    private Instant createdAt;
}
