package com.rovines.online.store.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.rovines.online.store.R;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.payload.request.LoginRequest;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.services.UserService;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private TextView sign_up_text_view;
    private TextView email;
    private TextView password;
    private Button login_button;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initializeView();
        initializeService();

        sign_up_text_view.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        login_button.setOnClickListener(v -> {
            LoginRequest loginRequest = LoginRequest.builder()
                    .email(email.getText().toString())
                    .password(password.getText().toString())
                    .build();

            login(loginRequest);
        });
    }

    private void initializeView() {
        this.sign_up_text_view = findViewById(R.id.sign_up_text_view);
        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.login_button = findViewById(R.id.login_button);
    }

    private void initializeService() {
        this.userService = new UserService(RetrofitClient.getUserRepository());
    }

    public void login(LoginRequest loginRequest) {
        userService.login(loginRequest, new ApiCallback<User>() {
            @Override
            public void onSuccess(SuccessResponse<User> successResponse) {
                Gson gson = new Gson();

                User user = successResponse.getData();
                String userJson = gson.toJson(user);

                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", userJson);
                editor.apply();


                Intent intent = new Intent(LoginActivity.this, StoreActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, successResponse.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Toast.makeText(LoginActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(SuccessResponse<List<User>> successResponse, Boolean fetch) {

            }
        });
    }
}
