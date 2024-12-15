package com.online.store.server.controllers.implementations;

import com.online.store.server.controllers.AuthController;
import com.online.store.server.payload.api.SuccessResponse;
import com.online.store.server.payload.request.LoginRequest;
import com.online.store.server.payload.request.RegisterRequest;
import com.online.store.server.payload.request.UpdateProfileRequest;
import com.online.store.server.services.AuthService;
import com.online.store.server.utils.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.CREATED,
                "Success register.",
                authService.register(registerRequest));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success login.",
                authService.login(loginRequest));
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<SuccessResponse> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest, @PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success Updating Profile",
                authService.updateProfile(updateProfileRequest, id)
        );
    }
}
