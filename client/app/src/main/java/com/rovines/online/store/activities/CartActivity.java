package com.rovines.online.store.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rovines.online.store.R;
import com.rovines.online.store.adapters.CartItemRecycleViewAdapter;
import com.rovines.online.store.helpers.CartManager;
import com.rovines.online.store.models.CartItem;
import com.rovines.online.store.models.User;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartItemRecyclerView;
    private CartManager cartManager;
    private TextView tvTotalPrice;
    private Double total_price = 0.0;
    List<CartItem> cartItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        initializeService();
        initializeView();
    }

    private void initializeView() {
        this.cartItemRecyclerView = findViewById(R.id.cartItemRecyclerView);
        this.tvTotalPrice = findViewById(R.id.tvTotalPrice);

        this.cartItemRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        cartItems = cartManager.getAllCartItem();

        for (CartItem cartItem : cartItems) {
            total_price += (cartItem.getPrice() * cartItem.getQuantity());
        }

        this.tvTotalPrice.setText("Rp" + total_price.toString());

        CartItemRecycleViewAdapter cartItemRecycleViewAdapter = new CartItemRecycleViewAdapter(this, cartItems, cartManager);
        this.cartItemRecyclerView.setAdapter(cartItemRecycleViewAdapter);


        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", null);
        if (userJson == null) {
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(CartActivity.this, "Silahkan Login Terlebih Dahulu.", Toast.LENGTH_LONG).show();
        }
    }

    private void initializeService() {
        this.cartManager = CartManager.getInstance(this);
    }
}
