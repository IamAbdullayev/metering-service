package com.ramazan.metering.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
@Builder
public class ResponseExceptionBody {
    LocalDateTime timestamp;
    HttpStatus status;
    String message;
    String path;
}
