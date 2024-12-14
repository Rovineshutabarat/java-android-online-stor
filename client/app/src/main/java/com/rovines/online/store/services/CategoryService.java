package com.rovines.online.store.services;

import com.google.gson.Gson;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.models.Category;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.repositories.CategoryRepository;

import java.util.List;

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
}
