package com.rovines.online.store.services;

import com.google.gson.Gson;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.repositories.CategoryRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void getAllCategories(ApiCallback<Category> callback) {
        Call<SuccessResponse<List<Category>>> call = categoryRepository.getAllCategories();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SuccessResponse<List<Category>>> call, Response<SuccessResponse<List<Category>>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body(), true);
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    callback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<List<Category>>> call, Throwable t) {
            }
        });
    }

    public CompletableFuture<Category> getCategoryById(Integer id) {
        CompletableFuture<Category> future = new CompletableFuture<>();
        Call<SuccessResponse<Category>> call = categoryRepository.getCategoryById(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SuccessResponse<Category>> call, Response<SuccessResponse<Category>> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    future.complete(response.body().getData());
                } else {
                    future.completeExceptionally(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<Category>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    public void createCategory(Category category, ApiCallback<Category> callback) {
        Call<SuccessResponse<Category>> call = categoryRepository.createCategory(category);
        call.enqueue(new Callback<SuccessResponse<Category>>() {
            @Override
            public void onResponse(Call<SuccessResponse<Category>> call, Response<SuccessResponse<Category>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    callback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<Category>> call, Throwable t) {

            }
        });
    }

    public void updateCategory(Category category, Integer id, ApiCallback<Category> callback) {
        Call<SuccessResponse<Category>> call = categoryRepository.updateCategory(category, id);
        call.enqueue(new Callback<SuccessResponse<Category>>() {
            @Override
            public void onResponse(Call<SuccessResponse<Category>> call, Response<SuccessResponse<Category>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    callback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<Category>> call, Throwable t) {

            }
        });
    }

    public void deleteCategory(Integer id, ApiCallback<Category> callback) {
        Call<SuccessResponse<Category>> call = categoryRepository.deleteCategory(id);
        ;
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SuccessResponse<Category>> call, Response<SuccessResponse<Category>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    callback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<Category>> call, Throwable t) {

            }
        });
    }

}
