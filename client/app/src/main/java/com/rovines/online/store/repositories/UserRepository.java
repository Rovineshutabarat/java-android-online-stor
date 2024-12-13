package com.rovines.online.store.repositories;

import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.request.LoginRequest;
import com.rovines.online.store.payload.request.RegisterRequest;
import com.rovines.online.store.services.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRepository {
    @POST("/auth/register")
    Call<ApiResponse<User>> register(@Body RegisterRequest registerRequest);

    @POST("/auth/login")
    Call<ApiResponse<User>> login(@Body LoginRequest loginRequest);
}
