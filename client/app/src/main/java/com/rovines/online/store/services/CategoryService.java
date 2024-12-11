package com.rovines.online.store.services;

import com.rovines.online.store.models.Category;
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

    public void getAllCategories(final FetchCallback<Category> callback) {
        Call<ApiResponse<List<Category>>> call = categoryRepository.getAllCategories();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
