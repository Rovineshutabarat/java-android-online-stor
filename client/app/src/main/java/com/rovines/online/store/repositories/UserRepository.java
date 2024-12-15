package com.rovines.online.store.repositories;

import com.rovines.online.store.models.UpdateProfileRequest;
import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.request.LoginRequest;
import com.rovines.online.store.payload.request.RegisterRequest;
import com.rovines.online.store.payload.api.SuccessResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserRepository {
    @POST("/auth/register")
    Call<SuccessResponse<User>> register(@Body RegisterRequest registerRequest);

    @POST("/auth/login")
    Call<SuccessResponse<User>> login(@Body LoginRequest loginRequest);

    @PUT("/auth/update/{id}")
    Call<SuccessResponse<User>> updateProfile(@Body UpdateProfileRequest updateProfileRequest, @Path("id") Integer id);
}
