package com.rovines.online.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rovines.online.store.R;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.helpers.Loading;
import com.rovines.online.store.helpers.RetrofitClient;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.payload.request.ProductRequest;
import com.rovines.online.store.services.CategoryService;
import com.rovines.online.store.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;


public class CreateProductActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText price;
    private EditText image;
    private Spinner category;
    private Loading loading;
    private CategoryService categoryService;
    private Integer selectedCategoryId;
    private List<Category> categories;
    private Button create_product_button;
    private ProductService productService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product_activity);
        initializeView();
        initializeService();
        fetchCategory();

        create_product_button.setOnClickListener(v -> {
            ProductRequest productRequest = ProductRequest.builder()
                    .name(name.getText().toString())
                    .description(description.getText().toString())
                    .price(price.getText().toString())
                    .image_url(image.getText().toString())
                    .category_id(selectedCategoryId)
                    .build();

            productService.createProduct(productRequest, new ApiCallback<Product>() {
                @Override
                public void onSuccess(SuccessResponse<Product> successResponse) {
                    Toast.makeText(CreateProductActivity.this, "Berhasil Tambah Produk", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateProductActivity.this, ProductActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onSuccess(SuccessResponse<List<Product>> successResponse, Boolean fetch) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {
                    Toast.makeText(CreateProductActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void initializeView() {
        this.name = findViewById(R.id.name);
        this.description = findViewById(R.id.description);
        this.price = findViewById(R.id.price);
        this.image = findViewById(R.id.image);
        this.category = findViewById(R.id.category);
        this.create_product_button = findViewById(R.id.create_product_button);
        productService = new ProductService(RetrofitClient.getProductRepository());
    }

    private void initializeService() {
        this.loading = new Loading(this);
        this.categoryService = new CategoryService(RetrofitClient.getCategoryRepository());
    }

    private void fetchCategory() {
        loading.show();
        categoryService.getAllCategories(new ApiCallback<Category>() {
            @Override
            public void onSuccess(SuccessResponse<Category> successResponse) {

            }

            @Override
            public void onSuccess(SuccessResponse<List<Category>> successResponse, Boolean fetch) {
                categories = successResponse.getData();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        CreateProductActivity.this,
                        android.R.layout.simple_spinner_item,
                        successResponse.getData().stream()
                                .map(Category::getName)
                                .collect(Collectors.toList())
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(adapter);

                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCategoryId = categories.get(position).getId();
                        Log.d("Selected Category", "ID: " + selectedCategoryId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedCategoryId = null;
                    }
                });
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {

            }
        });
    }
}
