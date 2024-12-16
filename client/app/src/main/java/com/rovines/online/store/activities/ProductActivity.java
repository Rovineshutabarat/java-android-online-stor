package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rovines.online.store.R;
import com.rovines.online.store.adapters.BottomNavigationAdapter;
import com.rovines.online.store.adapters.CategoryDashboardRecycleViewAdapter;
import com.rovines.online.store.adapters.ProductDashboardRecycleViewAdapter;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.listeners.ActionListener;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.services.ProductService;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private Button add_product_button;
    private RecyclerView productItemRecyclerView;
    private RecyclerView bottomNavigationRecycleView;
    private Loading loading;
    private ProductService productService;
    private ProductDashboardRecycleViewAdapter productDashboardRecycleViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        initializeView();
        initializeService();
        fetchProducts();

        this.add_product_button.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, CreateProductActivity.class);
            startActivity(intent);
        });
    }

    private void initializeView() {
        this.add_product_button = findViewById(R.id.add_product_button);
        this.productItemRecyclerView = findViewById(R.id.productItemRecyclerView);
        this.bottomNavigationRecycleView = findViewById(R.id.bottomNavigationRecycleView);

        bottomNavigationRecycleView.setLayoutManager(new GridLayoutManager(this, 4));

        BottomNavigationAdapter bottomNavigationAdapter = new BottomNavigationAdapter(this);
        bottomNavigationRecycleView.setAdapter(bottomNavigationAdapter);

        productItemRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
    }

    private void initializeService() {
        this.loading = new Loading(this);
        productService = new ProductService(RetrofitClient.getProductRepository());
    }

    private void fetchProducts() {
        loading.show();
        productService.getAllProducts(new ApiCallback<Product>() {
            @Override
            public void onSuccess(SuccessResponse<Product> successResponse) {

            }

            @Override
            public void onSuccess(SuccessResponse<List<Product>> successResponse, Boolean fetch) {
                productDashboardRecycleViewAdapter = new ProductDashboardRecycleViewAdapter(ProductActivity.this,
                        successResponse.getData(), new ActionListener() {
                    @Override
                    public void onDelete(Integer id) {
                        productService.deleteCategory(id, new ApiCallback<Product>() {
                            @Override
                            public void onSuccess(SuccessResponse<Product> successResponse) {
                                Toast.makeText(ProductActivity.this, "Berhasil Hapus Product.", Toast.LENGTH_LONG).show();
                                fetchProducts();
                            }

                            @Override
                            public void onSuccess(SuccessResponse<List<Product>> successResponse, Boolean fetch) {

                            }

                            @Override
                            public void onError(ErrorResponse errorResponse) {
                                Toast.makeText(ProductActivity.this, successResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onEdit(Integer id) {
                        Intent intent = new Intent(ProductActivity.this, UpdateProductActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
                productItemRecyclerView.setAdapter(productDashboardRecycleViewAdapter);
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Toast.makeText(ProductActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
