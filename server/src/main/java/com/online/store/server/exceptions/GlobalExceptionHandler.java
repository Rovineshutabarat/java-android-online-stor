package com.online.store.server.exceptions;

import com.online.store.server.payload.api.ErrorResponse;
import com.online.store.server.payload.api.ValidationErrorResponse;
import com.online.store.server.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        log.error(request.getRemoteAddr(), exception.getMessage());
        return ResponseBuilder.createHttpValidationErrorResponse(exception, request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            HttpServletRequest request) {
        log.error(request.getRemoteAddr(), exception.getMessage());
        return ResponseBuilder.createHttpErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException exception,
            HttpServletRequest request) {
        log.error(request.getRemoteAddr(), exception.getMessage());
        return ResponseBuilder.createHttpErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), request);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateElementException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateElementException(
            DuplicateElementException exception,
            HttpServletRequest request) {
        log.error(request.getRemoteAddr(), exception.getMessage());
        return ResponseBuilder.createHttpErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request);
    }
}
