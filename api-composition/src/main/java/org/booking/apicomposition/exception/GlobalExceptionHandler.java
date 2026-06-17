package org.booking.apicomposition.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.booking.apicomposition.exception.custom.*;
import org.booking.apicomposition.exception.dto.ErrorCode;
import org.booking.apicomposition.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleServiceUnavailableException(
            ServiceUnavailableException ex,
            HttpServletRequest request
    ) {
        log.warn("Service unavailable: {}", ex.getMessage());

        return new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ClientServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleClientServiceUnavailableException(
            ClientServiceUnavailableException ex,
            HttpServletRequest request
    ) {
        log.warn("Client Service unavailable: {}", ex.getMessage());

        return new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error occurred", ex);

        return new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }
}

