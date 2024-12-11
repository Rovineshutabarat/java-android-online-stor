package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Product;
import com.rovines.online.store.services.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductRepository {
    @GET("product")
    Call<ApiResponse<List<Product>>> getAllProducts();
    @GET("product/{id}")
    Call<ApiResponse<Product>> getProductById(@Path("id") Integer id);
}
