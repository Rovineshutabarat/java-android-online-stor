package com.rovines.online.store.payload.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String status;
    private Integer code;
    private String message;
    private String path;
    private String timestamp;
}
