package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rovines.online.store.R;

public class PaymentMethodActivity extends AppCompatActivity {
    private Button btn_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method_activity);
        initialize();
        btn_confirm.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentMethodActivity.this, OrderActivity.class);
            startActivity(intent);
        });
    }

    private void initialize() {
        this.btn_confirm = findViewById(R.id.btn_confirm);
    }
}
