package com.rovines.online.store.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rovines.online.store.R;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.services.ProductService;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView product_name;
    private ImageView product_image;
    private TextView product_description;
    private TextView product_price;
    private TextView product_category_name;
    private ProductService productService;
    private Loading loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        initializeServices();
        initialize();
        fetchProduct(getIntent().getIntExtra("id", 1));
    }


    private void initialize() {
        this.product_name = findViewById(R.id.product_name);
        this.product_image = findViewById(R.id.product_image);
        this.product_price = findViewById(R.id.product_price);
        this.product_description = findViewById(R.id.product_description);
        this.product_category_name = findViewById(R.id.product_category_name);
    }

    private void initializeServices() {
        this.productService = new ProductService(RetrofitClient.getProductRepository());
        this.loading = new Loading(this);
    }

    private void fetchProduct(Integer id) {
        loading.show();
        productService.getProductById(id).thenAccept(product -> runOnUiThread(() -> {
            if (product != null) {
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
