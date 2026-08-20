package com.spin.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.add(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage())));
        exception.getBindingResult().getGlobalErrors().forEach(globalError ->
                errors.add(new ValidationError(globalError.getObjectName(), globalError.getDefaultMessage())));

        ValidationErrorResponse response = new ValidationErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                request.getRequestURI(),
                errors);

        return ResponseEntity.badRequest().body(response);
    }

    public record ValidationErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String path,
            List<ValidationError> errors) {
    }

    public record ValidationError(String field, String message) {
    }
}