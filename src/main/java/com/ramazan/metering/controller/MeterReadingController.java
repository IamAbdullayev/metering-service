package com.ramazan.metering.controller;

import com.ramazan.metering.controller.dto.MeterReadingRequestDto;
import com.ramazan.metering.controller.dto.MeterReadingResponseDto;
import com.ramazan.metering.service.MeterReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meter-readings")
@RequiredArgsConstructor
@Tag(name = "Meter readings API", description = "Operations with meter readings (create, get, list)")
public class MeterReadingController {

    private final MeterReadingService meterReadingService;

    @Operation(
            summary = "Create a new meter reading",
            description = "Creates a new meter reading for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meter reading successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterReadingResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @PostMapping
    public ResponseEntity<MeterReadingResponseDto> createReading(
            @Valid @RequestBody MeterReadingRequestDto requestDto,
            @AuthenticationPrincipal(expression = "claims['sub']") String sub) {
        return ResponseEntity.ok(meterReadingService.createMeterReading(requestDto, sub));
    }

    @Operation(
            summary = "Get all meter readings",
            description = "Get all meter readings for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of meter readings",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MeterReadingResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @GetMapping
    public ResponseEntity<List<MeterReadingResponseDto>> getReadings(
            @AuthenticationPrincipal(expression = "claims['sub']") String sub) {
        return ResponseEntity.ok(meterReadingService.getAllReadings(sub));
    }

    @Operation(
            summary = "Get meter reading by ID",
            description = "Fetch a specific meter reading for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meter reading successfully found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterReadingResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided"),
            @ApiResponse(responseCode = "404", description = "Meter reading not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MeterReadingResponseDto> getReadingById(
            @PathVariable UUID id,
            @AuthenticationPrincipal(expression = "claims['sub']") String sub
    ) {
        return ResponseEntity.ok(meterReadingService.getReadingById(id, sub));
    }
}
