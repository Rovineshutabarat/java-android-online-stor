package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.rovines.online.store.MainActivity;
import com.rovines.online.store.R;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.request.RegisterRequest;
import com.rovines.online.store.services.UserService;

public class RegisterActivity extends AppCompatActivity {

    private TextView sign_in_text_view;
    private TextView email;
    private TextView username;
    private TextView password;
    private Button register_button;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initializeView();
        initializeService();

        sign_in_text_view.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        register_button.setOnClickListener(v -> {
            RegisterRequest registerRequest = RegisterRequest.builder()
                    .username(username.getText().toString())
                    .email(email.getText().toString())
                    .password(password.getText().toString())
                    .build();

            register(registerRequest);
        });

    }

    private void initializeView() {
        this.sign_in_text_view = findViewById(R.id.sign_in_text_view);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.register_button = findViewById(R.id.register_button);
    }

    private void initializeService() {
        this.userService = new UserService(RetrofitClient.getUserRepository(), this);
    }

    public void register(RegisterRequest registerRequest) {
        userService.register(registerRequest)
                .thenAccept(apiResponse -> {
                    if (apiResponse != null) {
                        if (apiResponse.getCode() == 201 || apiResponse.getCode() == 200) {
                            runOnUiThread(() -> {
                                Intent intent = new Intent(this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        }
                    }
                })
                .exceptionally(throwable -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Gagal Registrasi", Toast.LENGTH_LONG).show();
                    });
                    return null;
                });
    }

}
