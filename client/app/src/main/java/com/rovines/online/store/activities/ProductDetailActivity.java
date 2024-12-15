package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rovines.online.store.R;
import com.rovines.online.store.helpers.CartManager;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.CartItem;
import com.rovines.online.store.services.ProductService;

import java.util.Random;
import java.util.UUID;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView product_name;
    private ImageView product_image;
    private TextView product_description;
    private TextView product_price;
    private TextView product_category_name;
    private LinearLayout add_to_cart_button;
    private ProductService productService;
    private Loading loading;
    private CartManager cartManager;
    private Integer product_id;
    private String product_image_url;
    private Double price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        initializeServices();
        initialize();
        fetchProduct(getIntent().getIntExtra("id", 1));

        this.add_to_cart_button.setOnClickListener(v -> {
            cartManager.addToCart(CartItem.builder()
                    .id(product_id)
                    .name(product_name.getText().toString())
                    .price(price)
                    .image_url(product_image_url)
                    .quantity(1)
                    .build());
            Toast.makeText(ProductDetailActivity.this, "Berhasil Tambah Produk Ke Keranjang.", Toast.LENGTH_LONG).show();
        });
    }


    private void initialize() {
        this.product_name = findViewById(R.id.product_name);
        this.product_image = findViewById(R.id.product_image);
        this.product_price = findViewById(R.id.product_price);
        this.product_description = findViewById(R.id.product_description);
        this.product_category_name = findViewById(R.id.product_category_name);
        this.add_to_cart_button = findViewById(R.id.add_to_cart_button);
    }

    private void initializeServices() {
        this.productService = new ProductService(RetrofitClient.getProductRepository());
        this.loading = new Loading(this);
        this.cartManager = CartManager.getInstance(this);
    }

    private void fetchProduct(Integer id) {
        loading.show();
        productService.getProductById(id).thenAccept(product -> runOnUiThread(() -> {
            if (product != null) {
                product_id = product.getId();
                product_image_url = product.getImage_url();
                price = product.getPrice();
                product_name.setText(product.getName());
                product_price.setText(String.format("Rp.%.2f", product.getPrice()));
                product_description.setText(product.getDescription());
                product_category_name.setText(product.getCategory().getName());
                if (product.getImage_url() != null) {
                    Glide.with(this)
                            .load(product.getImage_url())
                            .into(this.product_image);
                }
            }
            loading.dismiss();
        })).exceptionally(throwable -> {
            runOnUiThread(() -> {
                loading.dismiss();
                Log.e("ProductDetailActivity", "Error fetching product", throwable);
            });
            return null;
        });
    }
}
