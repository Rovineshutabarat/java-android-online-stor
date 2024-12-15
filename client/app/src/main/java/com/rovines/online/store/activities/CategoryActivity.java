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
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.listeners.ActionListener;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.services.CategoryService;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView cartItemRecyclerView;
    private RecyclerView bottomNavigationRecycleView;
    private Button add_category_button;
    private Loading loading;
    private CategoryService categoryService;
    private CategoryDashboardRecycleViewAdapter categoryDashboardRecycleViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        initializeView();
        initializeService();
        fetchCategories();

        this.add_category_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateCategoryActivity.class);
            startActivity(intent);
        });
    }

    private void initializeView() {
        this.cartItemRecyclerView = findViewById(R.id.cartItemRecyclerView);
        this.bottomNavigationRecycleView = findViewById(R.id.bottomNavigationRecycleView);
        this.add_category_button = findViewById(R.id.add_category_button);

        bottomNavigationRecycleView.setLayoutManager(new GridLayoutManager(this, 4));

        BottomNavigationAdapter bottomNavigationAdapter = new BottomNavigationAdapter(this);
        bottomNavigationRecycleView.setAdapter(bottomNavigationAdapter);

        cartItemRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
    }

    private void initializeService() {
        this.loading = new Loading(this);
        this.categoryService = new CategoryService(RetrofitClient.getCategoryRepository());
    }

    private void fetchCategories() {
        loading.show();
        categoryService.getAllCategories(new ApiCallback<Category>() {
            @Override
            public void onSuccess(SuccessResponse<Category> successResponse) {

            }

            @Override
            public void onSuccess(SuccessResponse<List<Category>> successResponse, Boolean fetch) {
                categoryDashboardRecycleViewAdapter = new CategoryDashboardRecycleViewAdapter(CategoryActivity.this,
                        successResponse.getData(),
                        new ActionListener() {
                            @Override
                            public void onDelete(Integer id) {
                                categoryService.deleteCategory(id, new ApiCallback<Category>() {
                                    @Override
                                    public void onSuccess(SuccessResponse<Category> successResponse) {
                                        Toast.makeText(CategoryActivity.this, "Berhasil Hapus Category.", Toast.LENGTH_LONG).show();
                                        fetchCategories();
                                    }

                                    @Override
                                    public void onSuccess(SuccessResponse<List<Category>> successResponse, Boolean fetch) {

                                    }

                                    @Override
                                    public void onError(ErrorResponse errorResponse) {

                                    }
                                });
                            }

                            @Override
                            public void onEdit(Integer id) {
                                Intent intent = new Intent(CategoryActivity.this,UpdateCategoryActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        });
                cartItemRecyclerView.setAdapter(categoryDashboardRecycleViewAdapter);
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Toast.makeText(CategoryActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
