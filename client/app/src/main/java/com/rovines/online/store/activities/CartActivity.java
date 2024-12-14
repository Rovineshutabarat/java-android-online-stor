package com.rovines.online.store.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rovines.online.store.R;
import com.rovines.online.store.adapters.CartItemRecycleViewAdapter;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartItemRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        initializeView();
    }

    private void initializeView() {
        this.cartItemRecyclerView = findViewById(R.id.cartItemRecyclerView);

        this.cartItemRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "nama", "desc", 20000.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHKpC8YpsUM8vujw3jpbwxltYSyX30dn0mhBRYe5hLWfjMWunQey1gffykqSmOPZv3HQg&usqp=CAU", 1, Category.builder()
                .name("alndlkasnd")
                .build()));products.add(new Product(1, "nama", "desc", 20000.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHKpC8YpsUM8vujw3jpbwxltYSyX30dn0mhBRYe5hLWfjMWunQey1gffykqSmOPZv3HQg&usqp=CAU", 1, Category.builder()
                .name("alndlkasnd")
                .build()));

        CartItemRecycleViewAdapter cartItemRecycleViewAdapter = new CartItemRecycleViewAdapter(this, products);
        this.cartItemRecyclerView.setAdapter(cartItemRecycleViewAdapter);
    }
}
