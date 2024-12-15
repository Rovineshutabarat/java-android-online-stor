package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rovines.online.store.R;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.services.CategoryService;

import java.util.List;

public class CreateCategoryActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText image;
    private Button create_category_button;
    private CategoryService categoryService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_category_activity);
        initializeView();
        initializeService();

        create_category_button.setOnClickListener(v -> {
            Category category = Category.builder()
                    .name(name.getText().toString())
                    .description(description.getText().toString())
                    .image_url(image.getText().toString())
                    .build();
            categoryService.createCategory(category, new ApiCallback<Category>() {
                @Override
                public void onSuccess(SuccessResponse<Category> successResponse) {
                    Toast.makeText(CreateCategoryActivity.this, "Berhasil Tambah Kategori", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateCategoryActivity.this, CategoryActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onSuccess(SuccessResponse<List<Category>> successResponse, Boolean fetch) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {
                    Toast.makeText(CreateCategoryActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void initializeView() {
        this.name = findViewById(R.id.name);
        this.description = findViewById(R.id.description);
        this.image = findViewById(R.id.image);
        this.create_category_button = findViewById(R.id.create_category_button);
    }

    private void initializeService() {
        this.categoryService = new CategoryService(RetrofitClient.getCategoryRepository());
    }
}
