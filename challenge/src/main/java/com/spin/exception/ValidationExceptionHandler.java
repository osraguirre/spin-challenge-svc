package com.spin.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.add(fieldError.getDefaultMessage()));
        exception.getBindingResult().getGlobalErrors().forEach(globalError ->
                errors.add(globalError.getDefaultMessage()));

        ValidationErrorResponse response = new ValidationErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                request.getRequestURI(),
                errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(TransactionValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleTransactionValidationException(
            TransactionValidationException exception, HttpServletRequest request) {
        ValidationErrorResponse response = new ValidationErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                request.getRequestURI(),
                List.of(exception.getMessage()));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationErrorResponse> handleUnexpectedException(
            Exception exception, HttpServletRequest request) {
        log.error("Unhandled exception processing request: {}", request.getRequestURI(), exception);

        ValidationErrorResponse response = new ValidationErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                request.getRequestURI(),
                List.of());

        return ResponseEntity.internalServerError().body(response);
    }

    public record ValidationErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String path,
            List<String> errors) {
    }
}