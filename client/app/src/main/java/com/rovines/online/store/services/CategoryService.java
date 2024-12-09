package com.rovines.online.store.services;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.rovines.online.store.adapters.CategoryRecycleViewAdapter;
import com.rovines.online.store.models.Category;
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

    public void getAllCategories(final FetchCallback<Category> callback) {
        Call<List<Category>> call = categoryRepository.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
