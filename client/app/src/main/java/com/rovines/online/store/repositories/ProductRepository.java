package com.rovines.online.store.repositories;

import com.rovines.online.store.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductRepository {
    @GET("product")
    Call<List<Product>> getAllProducts();
}
