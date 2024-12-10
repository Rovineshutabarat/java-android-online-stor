package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductRepository {
    @GET("product")
    Call<List<Product>> getAllProducts();
    @GET("product/{id}")
    Call<Product> getProductById(@Path("id") Integer id);
}
