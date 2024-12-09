package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryRepository {
    @GET("category")
    Call<List<Category>> getAllCategories();
}
