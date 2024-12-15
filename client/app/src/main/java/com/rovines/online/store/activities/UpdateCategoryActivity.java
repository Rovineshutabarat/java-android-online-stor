package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rovines.online.store.R;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.services.CategoryService;

import java.util.List;


public class UpdateCategoryActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText image;
    private Button update_category_button;
    private CategoryService categoryService;
    private Integer id;
    private Loading loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_category_activity);
        id = getIntent().getIntExtra("id", 0);
        initializeView();
        initializeService();
        fetchCategory(getIntent().getIntExtra("id", 1));

        this.update_category_button.setOnClickListener(v -> {
            Category category = Category.builder()
                    .name(name.getText().toString())
                    .description(description.getText().toString())
                    .image_url(image.getText().toString())
                    .build();
            categoryService.updateCategory(category, id, new ApiCallback<Category>() {
                @Override
                public void onSuccess(SuccessResponse<Category> successResponse) {
                    Toast.makeText(UpdateCategoryActivity.this, "Berhasil Update Kategori", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UpdateCategoryActivity.this, CategoryActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onSuccess(SuccessResponse<List<Category>> successResponse, Boolean fetch) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {
                    Toast.makeText(UpdateCategoryActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void initializeView() {
        this.name = findViewById(R.id.name);
        this.description = findViewById(R.id.description);
        this.image = findViewById(R.id.image);
        this.update_category_button = findViewById(R.id.update_category_button);
    }

    private void initializeService() {
        this.categoryService = new CategoryService(RetrofitClient.getCategoryRepository());
        loading = new Loading(this);
    }

    private void fetchCategory(Integer id) {
        loading.show();
        categoryService.getCategoryById(id).thenAccept(category -> runOnUiThread(() -> {
            if (category != null) {
                name.setText(category.getName());
                description.setText(category.getDescription());
                image.setText(category.getImage_url());
            }
            loading.dismiss();
        })).exceptionally(throwable -> {
            runOnUiThread(() -> {
                loading.dismiss();
                Log.e("Update Category Activity", "Error fetching category", throwable);
            });
            return null;
        });
    }
}
