package com.ramazan.metering.controller;

import com.ramazan.metering.controller.dto.MeterReadingResponseDto;
import com.ramazan.metering.controller.dto.UserResponseDto;
import com.ramazan.metering.model.entity.User;
import com.ramazan.metering.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Operations with users (create, get, list)")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "...",
            description = "....")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "....",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestParam String sub) {
        return ResponseEntity.status(201).body(userService.createUser(sub));
    }

    @Operation(
            summary = "....",
            description = "....")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "....",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(
            summary = "Get user by id",
            description = ".....")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "....",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - no valid JWT token provided")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
