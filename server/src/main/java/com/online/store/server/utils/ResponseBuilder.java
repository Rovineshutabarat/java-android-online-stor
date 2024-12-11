package com.online.store.server.utils;

import com.online.store.server.enums.ApiStatus;
import com.online.store.server.payload.api.ErrorResponse;
import com.online.store.server.payload.api.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

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
}
