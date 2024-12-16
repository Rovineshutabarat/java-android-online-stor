package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.SuccessResponse;
import com.rovines.online.store.payload.request.ProductRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductRepository {
    @GET("product")
    Call<SuccessResponse<List<Product>>> getAllProducts();

    @GET("product/{id}")
    Call<SuccessResponse<Product>> getProductById(@Path("id") Integer id);

    @POST("product")
    Call<SuccessResponse<Product>> createProduct(@Body ProductRequest productRequest);

    @PUT("product/{id}")
    Call<SuccessResponse<Product>> updateProduct(@Path("id") Integer id, @Body ProductRequest productRequest);

    @DELETE("/product/{id}")
    Call<SuccessResponse<Product>> deleteProduct(@Path("id") Integer id);
}
