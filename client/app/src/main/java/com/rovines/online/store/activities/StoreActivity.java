package com.rovines.online.store.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rovines.online.store.R;
import com.rovines.online.store.adapters.CategoryRecycleViewAdapter;
import com.rovines.online.store.adapters.ProductRecycleViewAdapter;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.models.Product;
import com.rovines.online.store.services.CategoryService;
import com.rovines.online.store.services.FetchCallback;
import com.rovines.online.store.services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView category_recycle_view;
    private RecyclerView popular_product_recycle_view;
    private RecyclerView all_product_grid_view;
    private ProductService productService;
    private CategoryService categoryService;
    private CategoryRecycleViewAdapter categoryRecycleViewAdapter;
    private ProductRecycleViewAdapter productRecycleViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);
        initialize();
    }

    private void initialize() {
        this.category_recycle_view = findViewById(R.id.category_recycle_view);
        this.popular_product_recycle_view = findViewById(R.id.popular_product_recycle_view);
        this.all_product_grid_view = findViewById(R.id.all_product_grid_view);

        this.productService = new ProductService(RetrofitClient.getProductRepository());
        this.categoryService = new CategoryService(RetrofitClient.getCategoryRepository());

        this.setCategoryRecycleViewAdapter();
        this.setPopularProductRecycleViewAdapter();
        setAllProductGridViewAdapter();
    }

    private void setCategoryRecycleViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.category_recycle_view.setLayoutManager(linearLayoutManager);

        categoryService.getAllCategories(new FetchCallback<Category>() {
            @Override
            public void onSuccess(List<Category> categories) {
                categoryRecycleViewAdapter = new CategoryRecycleViewAdapter(StoreActivity.this, categories);
                category_recycle_view.setAdapter(categoryRecycleViewAdapter);
                categoryRecycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("StoreActivity", "Error fetching categories", throwable);
            }
        });
    }

    private void setPopularProductRecycleViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.popular_product_recycle_view.setLayoutManager(linearLayoutManager);

        productService.getAllProducts(new FetchCallback<Product>() {
            @Override
            public void onSuccess(List<Product> products) {
                ProductRecycleViewAdapter productRecycleViewAdapter = new ProductRecycleViewAdapter(StoreActivity.this, products);
                popular_product_recycle_view.setAdapter(productRecycleViewAdapter);
                productRecycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("StoreActivity", "Error fetching products", throwable);
            }
        });
    }

    public void setAllProductGridViewAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        this.all_product_grid_view.setLayoutManager(gridLayoutManager);

        productService.getAllProducts(new FetchCallback<Product>() {
            @Override
            public void onSuccess(List<Product> products) {
                ProductRecycleViewAdapter productRecycleViewAdapter = new ProductRecycleViewAdapter(StoreActivity.this, products);
                all_product_grid_view.setAdapter(productRecycleViewAdapter);
                productRecycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("StoreActivity", "Error fetching products", throwable);
            }
        });
    }
}
