package com.rovines.online.store.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.request.LoginRequest;
import com.rovines.online.store.payload.request.RegisterRequest;
import com.rovines.online.store.repositories.UserRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final UserRepository userRepository;
    private final Context context;

    public UserService(UserRepository userRepository, Context context) {
        this.userRepository = userRepository;
        this.context = context;
    }

    public CompletableFuture<ApiResponse<User>> register(RegisterRequest registerRequest) {
        CompletableFuture<ApiResponse<User>> future = new CompletableFuture<>();
        Call<ApiResponse<User>> call = userRepository.register(registerRequest);

        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 201 || response.body().getCode() == 200) {
                            future.complete(response.body());
                        }
                    }
                } else {
                    future.completeExceptionally(new Exception("Gagal Registrasi"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }


    public CompletableFuture<ApiResponse<User>> login(LoginRequest loginRequest) {
        CompletableFuture<ApiResponse<User>> future = new CompletableFuture<>();
        Call<ApiResponse<User>> call = userRepository.login(loginRequest);
        call.enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 201 || response.body().getCode() == 200) {
                            future.complete(response.body());
                        }
                    }
                } else {
                    future.completeExceptionally(new Exception("Gagal Login"));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
