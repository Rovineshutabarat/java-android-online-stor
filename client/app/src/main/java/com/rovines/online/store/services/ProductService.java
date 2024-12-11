package com.rovines.online.store.services;

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

    public void getAllProducts(final FetchCallback<Product> callback) {
        Call<ApiResponse<List<Product>>> call = productRepository.getAllProducts();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (response.body().getCode() == 200 && response.isSuccessful()) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public CompletableFuture<Product> getProductById(Integer id) {
        CompletableFuture<Product> future = new CompletableFuture<>();
        Call<ApiResponse<Product>> call = productRepository.getProductById(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    future.complete(response.body().getData());
                } else {
                    future.completeExceptionally(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
