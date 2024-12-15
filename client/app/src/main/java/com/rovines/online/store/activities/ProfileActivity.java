package com.rovines.online.store.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rovines.online.store.R;
import com.rovines.online.store.adapters.BottomNavigationAdapter;
import com.rovines.online.store.models.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView edit_profile_button;
    private TextView cart_button;
    private TextView username;
    private TextView email;
    private TextView contact;
    private TextView address;
    private TextView order_button;
    private TextView adminpage_button;
    private TextView logout_button;
    private RecyclerView bottomNavigationRecycleView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initializeView();

        this.cart_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

        this.logout_button.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.remove("user");
            editor.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        this.edit_profile_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileFormActivity.class);
            startActivity(intent);
        });

        this.adminpage_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminDashboardActivity.class);
            startActivity(intent);
        });
    }

    private void initializeView() {
        this.edit_profile_button = findViewById(R.id.edit_profile_button);
        this.cart_button = findViewById(R.id.cart_button);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.address = findViewById(R.id.address);
        this.contact = findViewById(R.id.contact);
        this.order_button = findViewById(R.id.order_button);
        this.adminpage_button = findViewById(R.id.adminpage_button);
        this.logout_button = findViewById(R.id.logout_button);
        this.bottomNavigationRecycleView = findViewById(R.id.bottomNavigationRecycleView);

        bottomNavigationRecycleView.setLayoutManager(new GridLayoutManager(this, 4));

        BottomNavigationAdapter bottomNavigationAdapter = new BottomNavigationAdapter(this);
        bottomNavigationRecycleView.setAdapter(bottomNavigationAdapter);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", null);
        if (userJson != null) {
            User user = gson.fromJson(userJson, User.class);
            this.username.setText(user.getUsername());
            this.email.setText(user.getEmail());
            if (user.getContact() != null) {
                this.contact.setText(user.getContact());
            }
            if (user.getAddress() != null) {
                this.address.setText(user.getAddress());
            }
        } else {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(ProfileActivity.this, "Silahkan Login Terlebih Dahulu.", Toast.LENGTH_LONG).show();
        }
    }
}
