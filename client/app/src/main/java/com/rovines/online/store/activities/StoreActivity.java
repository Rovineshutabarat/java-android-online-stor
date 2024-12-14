package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rovines.online.store.R;
import com.rovines.online.store.adapters.CategoryRecycleViewAdapter;
import com.rovines.online.store.adapters.ProductRecycleViewAdapter;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.services.CategoryService;
import com.rovines.online.store.services.ProductService;

import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private RecyclerView categoryRecycleView;
    private RecyclerView popularProductRecycleView;
    private RecyclerView allProductGridView;
    private ProductService productService;
    private CategoryService categoryService;
    private CategoryRecycleViewAdapter categoryAdapter;
    private ProductRecycleViewAdapter popularProductAdapter;
    private ProductRecycleViewAdapter allProductAdapter;
    private Loading loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);
        initializeViews();
        initializeServices();
        fetchData();
    }

    private void initializeViews() {
        categoryRecycleView = findViewById(R.id.categoryRecycleView);
        popularProductRecycleView = findViewById(R.id.popularProductRecycleView);
        allProductGridView = findViewById(R.id.allProductGridView);

        categoryRecycleView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        popularProductRecycleView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        allProductGridView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initializeServices() {
        productService = new ProductService(RetrofitClient.getProductRepository());
        categoryService = new CategoryService(RetrofitClient.getCategoryRepository());
        loading = new Loading(this);
    }

    private void fetchData() {
        fetchCategories();
        fetchPopularProducts();
        fetchAllProducts();
    }

    private void fetchCategories() {
        loading.show();
        categoryService.getAllCategories(new ApiCallback<Category>() {
            @Override
            public void onSuccess(SuccessResponse<Category> successResponse) {

            }

            @Override
            public void onSuccess(SuccessResponse<List<Category>> successResponse, Boolean fetch) {
                categoryAdapter = new CategoryRecycleViewAdapter(StoreActivity.this, successResponse.getData());
                categoryRecycleView.setAdapter(categoryAdapter);
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Toast.makeText(StoreActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchPopularProducts() {
        loading.show();
        productService.getAllProducts(new ApiCallback<Product>() {
            @Override
            public void onSuccess(SuccessResponse<Product> successResponse) {

            }

            @Override
            public void onSuccess(SuccessResponse<List<Product>> successResponse, Boolean fetch) {
                popularProductAdapter = new ProductRecycleViewAdapter(
                        StoreActivity.this,
                        successResponse.getData(),
                        id -> {
                            Intent intent = new Intent(StoreActivity.this, ProductDetailActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }
                );
                popularProductRecycleView.setAdapter(popularProductAdapter);
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Toast.makeText(StoreActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchAllProducts() {
        loading.show();
        productService.getAllProducts(new ApiCallback<Product>() {
            @Override
            public void onSuccess(SuccessResponse<Product> successResponse) {

            }

            @Override
            public void onSuccess(SuccessResponse<List<Product>> successResponse, Boolean fetch) {
                allProductAdapter = new ProductRecycleViewAdapter(
                        StoreActivity.this,
                        successResponse.getData(),
                        id -> {
                            Intent intent = new Intent(StoreActivity.this, ProductDetailActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }
                );
                allProductGridView.setAdapter(allProductAdapter);
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Toast.makeText(StoreActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleError(String context, Throwable throwable) {
        Log.e("StoreActivity", "Error fetching " + context, throwable);
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        loading.dismiss();
    }
}