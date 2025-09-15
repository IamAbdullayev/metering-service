package com.ramazan.metering.exception.handler;

import com.ramazan.metering.exception.ForbiddenException;
import com.ramazan.metering.exception.NotFoundException;
import com.ramazan.metering.exception.ResponseExceptionBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseExceptionBody> handleNotFoundException(NotFoundException e,
                                                                         HttpServletRequest request) {
        return handleException(HttpStatus.NOT_FOUND, e, request, e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseExceptionBody> handleForbiddenException(ForbiddenException e,
                                                                          HttpServletRequest request) {
        return handleException(HttpStatus.FORBIDDEN, e, request, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseExceptionBody> handleIllegalArgumentException(IllegalArgumentException e,
                                                                                HttpServletRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, e, request, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseExceptionBody> handleGeneralException(Exception e,
                                                                 HttpServletRequest request) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, e, request, e.getMessage());
    }

    private ResponseEntity<ResponseExceptionBody> handleException(HttpStatus status,
                                                                  Exception e,
                                                                  HttpServletRequest request,
                                                                  String customMessage) {
        log.warn("{}: {}", e.getClass().getName(), e.getMessage(), e);
        ResponseExceptionBody exceptionBody = ResponseExceptionBody.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .message(customMessage != null ? customMessage : e.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(exceptionBody, status);
    }
}
