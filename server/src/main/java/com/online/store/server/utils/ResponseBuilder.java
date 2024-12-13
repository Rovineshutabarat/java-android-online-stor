package com.online.store.server.utils;

import com.online.store.server.enums.ApiStatus;
import com.online.store.server.payload.api.ErrorResponse;
import com.online.store.server.payload.api.SuccessResponse;
import com.online.store.server.payload.api.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    public static ResponseEntity<SuccessResponse> createHttpSuccessResponse(
            HttpStatus httpStatus,
            String message,
            Object data) {
        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .status(ApiStatus.SUCCESS.name())
                        .code(httpStatus.value())
                        .message(message)
                        .data(data)
                        .build(), httpStatus);
    }

    public static ResponseEntity<ErrorResponse> createHttpErrorResponse(
            HttpStatus httpStatus,
            String message,
            HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .status(ApiStatus.SUCCESS.name())
                        .code(httpStatus.value())
                        .message(message)
                        .path(httpServletRequest.getRequestURI())
                        .timestamp(new Date())
                        .build(), httpStatus);
    }

    public static ResponseEntity<ValidationErrorResponse> createHttpValidationErrorResponse(
            MethodArgumentNotValidException exception,
            HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                ValidationErrorResponse.builder()
                        .status(ApiStatus.VALIDATION_FAILED.name())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Validation Failed.")
                        .path(httpServletRequest.getRequestURI())
                        .timestamp(new Date())
                        .fieldErrors(extractFieldErrors(exception))
                        .build(), HttpStatus.BAD_REQUEST);
    }

    private static Map<String, String> extractFieldErrors(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        return fieldErrors;
    }
}


