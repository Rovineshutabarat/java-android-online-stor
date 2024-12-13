package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.rovines.online.store.MainActivity;
import com.rovines.online.store.R;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.payload.request.LoginRequest;
import com.rovines.online.store.services.UserService;

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
        this.userService = new UserService(RetrofitClient.getUserRepository(), this);
    }

    public void login(LoginRequest loginRequest) {
        userService.login(loginRequest)
                .thenAccept(apiResponse -> {
                    if (apiResponse != null) {
                        if (apiResponse.getCode() == 201 || apiResponse.getCode() == 200) {
                            runOnUiThread(() -> {
                                Intent intent = new Intent(this, StoreActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        }
                    }
                })
                .exceptionally(throwable -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Gagal Login", Toast.LENGTH_LONG).show();
                    });
                    return null;
                });
    }
}
