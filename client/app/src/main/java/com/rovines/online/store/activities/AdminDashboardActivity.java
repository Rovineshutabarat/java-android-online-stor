package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rovines.online.store.R;
import com.rovines.online.store.adapters.BottomNavigationAdapter;

public class AdminDashboardActivity extends AppCompatActivity {
    private RecyclerView bottomNavigationRecycleView;
    private Button edit_profile_button;
    private Button category_button;
    private Button product_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_activity);
        initializeView();

        this.edit_profile_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileFormActivity.class);
            startActivity(intent);
        });

        this.category_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
        });

        this.product_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
        });
    }

    private void initializeView() {
        this.bottomNavigationRecycleView = findViewById(R.id.bottomNavigationRecycleView);
        this.edit_profile_button = findViewById(R.id.edit_profile_button);
        this.category_button = findViewById(R.id.category_button);
        this.product_button = findViewById(R.id.product_button);


        bottomNavigationRecycleView.setLayoutManager(new GridLayoutManager(this, 4));

        BottomNavigationAdapter bottomNavigationAdapter = new BottomNavigationAdapter(this);
        bottomNavigationRecycleView.setAdapter(bottomNavigationAdapter);
    }
}
