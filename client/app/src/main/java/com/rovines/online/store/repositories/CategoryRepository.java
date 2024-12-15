package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Category;
import com.rovines.online.store.payload.api.SuccessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryRepository {
    @GET("/category")
    Call<SuccessResponse<List<Category>>> getAllCategories();

    @GET("/category/{id}")
    Call<SuccessResponse<Category>> getCategoryById(@Path("id") Integer id);

    @POST("category")
    Call<SuccessResponse<Category>> createCategory(@Body Category category);

    @PUT("category/{id}")
    Call<SuccessResponse<Category>> updateCategory(@Body Category category, @Path("id") Integer id);

    @DELETE("category/{id}")
    Call<SuccessResponse<Category>> deleteCategory(@Path("id") Integer id);
}
