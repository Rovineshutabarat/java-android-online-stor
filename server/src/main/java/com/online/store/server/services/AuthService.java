package com.online.store.server.services;

import com.online.store.server.models.User;
import com.online.store.server.payload.request.LoginRequest;
import com.online.store.server.payload.request.RegisterRequest;
import com.online.store.server.payload.request.UpdateProfileRequest;

public interface AuthService {
    User register(RegisterRequest registerRequest);

    User login(LoginRequest loginRequest);

    User updateProfile(UpdateProfileRequest updateProfileRequest , Integer id);
}
