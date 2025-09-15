package com.ramazan.metering.controller;

import com.ramazan.metering.controller.dto.MeterRequestDto;
import com.ramazan.metering.controller.dto.MeterResponseDto;
import com.ramazan.metering.service.MeterService;
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
@RequestMapping("/api/v1/meters")
@RequiredArgsConstructor
@Tag(name = "Meter API", description = "Operations with meters (create, get, list)")
public class MeterController {

    private final MeterService meterService;

    @Operation(
            summary = "Create a new meter",
            description = "Creates a new meter for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meter successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @PostMapping
    public ResponseEntity<MeterResponseDto> createMeter(
            @Valid @RequestBody MeterRequestDto requestDto,
            @AuthenticationPrincipal(expression = "claims['sub']") String sub
    ) {
        return ResponseEntity.ok(meterService.createMeter(requestDto, sub));
    }

    @Operation(
            summary = "Get all meters",
            description = "Fetch all meters for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of meters",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MeterResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @GetMapping
    public ResponseEntity<List<MeterResponseDto>> getAllMeters(
            @AuthenticationPrincipal(expression = "claims['sub']") String sub
    ) {
        return ResponseEntity.ok(meterService.getAllMeters(sub));
    }

    @Operation(
            summary = "Get meter by ID",
            description = "Fetch a specific meter for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meter successfully found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided"),
            @ApiResponse(responseCode = "404", description = "Meter not found or not accessible")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MeterResponseDto> getMeterById(
            @PathVariable UUID id,
            @AuthenticationPrincipal(expression = "claims['sub']") String sub
    ) {
        return ResponseEntity.ok(meterService.getMeterById(id, sub));
    }
}
