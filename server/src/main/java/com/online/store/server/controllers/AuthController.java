package com.online.store.server.controllers;

import com.online.store.server.payload.api.SuccessResponse;
import com.online.store.server.payload.request.LoginRequest;
import com.online.store.server.payload.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<SuccessResponse> register(RegisterRequest registerRequest);

    ResponseEntity<SuccessResponse> login(LoginRequest loginRequest);
}
