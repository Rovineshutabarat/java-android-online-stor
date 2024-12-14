package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Category;
import com.rovines.online.store.payload.api.SuccessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryRepository {
    @GET("category")
    Call<SuccessResponse<List<Category>>> getAllCategories();
}
