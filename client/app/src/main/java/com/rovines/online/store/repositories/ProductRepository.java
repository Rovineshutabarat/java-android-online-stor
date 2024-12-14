package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.SuccessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductRepository {
    @GET("product")
    Call<SuccessResponse<List<Product>>> getAllProducts();
    @GET("product/{id}")
    Call<SuccessResponse<Product>> getProductById(@Path("id") Integer id);
}
