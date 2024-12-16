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

public class UpdateProductActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText price;
    private EditText image;
    private Spinner category;
    private Loading loading;
    private CategoryService categoryService;
    private Integer selectedCategoryId;
    private List<Category> categories;
    private Button update_product_button;
    private ProductService productService;
    private Integer id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product_activity);
        initializeView();
        initializeService();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        fetchCategory();

        update_product_button.setOnClickListener(v -> {
            ProductRequest productRequest = ProductRequest.builder()
                    .name(name.getText().toString())
                    .description(description.getText().toString())
                    .price(price.getText().toString())
                    .image_url(image.getText().toString())
                    .category_id(selectedCategoryId)
                    .build();

            productService.updateProduct(id, productRequest, new ApiCallback<Product>() {
                @Override
                public void onSuccess(SuccessResponse<Product> successResponse) {
                    Toast.makeText(UpdateProductActivity.this, "Berhasil Update Produk", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UpdateProductActivity.this, ProductActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onSuccess(SuccessResponse<List<Product>> successResponse, Boolean fetch) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {
                    Toast.makeText(UpdateProductActivity.this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_LONG).show();
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
        this.update_product_button = findViewById(R.id.update_product_button);
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
                        UpdateProductActivity.this,
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        selectedCategoryId = null;
                    }
                });
                fetchProducts(id);
                loading.dismiss();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {

            }
        });
    }

    private void fetchProducts(Integer id) {
        loading.show();
        productService.getProductById(id).thenAccept(product -> runOnUiThread(() -> {
            if (product != null) {
                name.setText(product.getName());
                price.setText(product.getPrice().toString());
                description.setText(product.getDescription());
                image.setText(product.getImage_url());
                if (categories != null && product.getCategory() != null) {
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getId().equals(product.getCategory().getId())) {
                            category.setSelection(i);
                            selectedCategoryId = product.getCategory().getId();
                            break;
                        }
                    }
                }
            }
            loading.dismiss();
        })).exceptionally(throwable -> {
            runOnUiThread(() -> {
                loading.dismiss();
                Log.e("UpdateProductActivity", "Error fetching product", throwable);
            });
            return null;
        });
    }
}