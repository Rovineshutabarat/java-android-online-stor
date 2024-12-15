package com.rovines.online.store.services;

import com.google.gson.Gson;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.models.UpdateProfileRequest;
import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.payload.request.LoginRequest;
import com.rovines.online.store.payload.request.RegisterRequest;
import com.rovines.online.store.repositories.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(RegisterRequest registerRequest, ApiCallback<User> apiCallback) {
        Call<SuccessResponse<User>> call = userRepository.register(registerRequest);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SuccessResponse<User>> call, Response<SuccessResponse<User>> response) {
                if (response.isSuccessful()) {
                    apiCallback.onSuccess(response.body());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    apiCallback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<User>> call, Throwable t) {

            }
        });
    }


    public void login(LoginRequest loginRequest, ApiCallback<User> apiCallback) {
        Call<SuccessResponse<User>> call = userRepository.login(loginRequest);
        call.enqueue(new Callback<SuccessResponse<User>>() {
            @Override
            public void onResponse(Call<SuccessResponse<User>> call, Response<SuccessResponse<User>> response) {
                if (response.isSuccessful()) {
                    apiCallback.onSuccess(response.body());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    apiCallback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<User>> call, Throwable t) {

            }
        });
    }

    public void updateProfile(UpdateProfileRequest updateProfileRequest, Integer id, ApiCallback<User> apiCallback) {
        Call<SuccessResponse<User>> call = userRepository.updateProfile(updateProfileRequest, id);

        call.enqueue(new Callback<SuccessResponse<User>>() {
            @Override
            public void onResponse(Call<SuccessResponse<User>> call, Response<SuccessResponse<User>> response) {
                if (response.isSuccessful()) {
                    apiCallback.onSuccess(response.body());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    apiCallback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<User>> call, Throwable t) {

            }
        });
    }
}
