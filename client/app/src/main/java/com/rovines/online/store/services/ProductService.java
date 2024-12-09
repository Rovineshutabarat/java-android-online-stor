package com.rovines.online.store.services;

import android.util.Log;

import com.rovines.online.store.models.Product;
import com.rovines.online.store.repositories.ProductRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public CompletableFuture<List<Product>> getAllProducts(final FetchCallback<Product> callback) {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();

        Call<List<Product>> call = productRepository.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t);
            }
        });

        return future;
    }
}
