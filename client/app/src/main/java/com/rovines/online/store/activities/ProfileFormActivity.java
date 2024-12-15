package com.rovines.online.store.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.rovines.online.store.R;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.UpdateProfileRequest;
import com.rovines.online.store.models.User;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.services.UserService;

import java.util.List;

public class ProfileFormActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText address;
    private EditText contact;
    private EditText password;
    private Button update_profile_button;
    private UserService userService;
    private Integer user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_form_activity);
        initializeView();
        initializeService();

        this.update_profile_button.setOnClickListener(v -> {
            UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                    .username(username.getText().toString())
                    .email(email.getText().toString())
                    .password(password.getText().toString())
                    .address(address.getText().toString())
                    .contact(contact.getText().toString())
                    .build();

            userService.updateProfile(updateProfileRequest, this.user_id, new ApiCallback<User>() {
                @Override
                public void onSuccess(SuccessResponse<User> successResponse) {
                    Gson gson = new Gson();

                    User user = successResponse.getData();
                    String userJson = gson.toJson(user);

                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", userJson);
                    editor.apply();


                    Intent intent = new Intent(ProfileFormActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    Toast.makeText(ProfileFormActivity.this, successResponse.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(SuccessResponse<List<User>> successResponse, Boolean fetch) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {
                    Toast.makeText(ProfileFormActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void initializeView() {
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.address = findViewById(R.id.address);
        this.contact = findViewById(R.id.contact);
        this.password = findViewById(R.id.password);
        this.update_profile_button = findViewById(R.id.update_profile_button);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", null);
        if (userJson != null) {
            User user = gson.fromJson(userJson, User.class);
            this.user_id = user.getId();
            this.username.setText(user.getUsername());
            this.email.setText(user.getEmail());
            this.password.setText(user.getPassword());
            this.address.setText(user.getAddress());
            this.contact.setText(user.getContact());
        }
    }

    private void initializeService() {
        this.userService = new UserService(RetrofitClient.getUserRepository());
    }
}
