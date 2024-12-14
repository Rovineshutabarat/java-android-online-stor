package com.rovines.online.store.services;

import com.google.gson.Gson;
import com.rovines.online.store.callbacks.ApiCallback;
import com.rovines.online.store.models.Product;
import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;
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

    public void getAllProducts(final ApiCallback<Product> callback) {
        Call<SuccessResponse<List<Product>>> call = productRepository.getAllProducts();
        call.enqueue(new Callback<SuccessResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<SuccessResponse<List<Product>>> call, Response<SuccessResponse<List<Product>>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body(), true);
                } else {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(
                            response.errorBody().charStream(),
                            ErrorResponse.class
                    );
                    callback.onError(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<List<Product>>> call, Throwable t) {

            }
        });
    }

    public CompletableFuture<Product> getProductById(Integer id) {
        CompletableFuture<Product> future = new CompletableFuture<>();
        Call<SuccessResponse<Product>> call = productRepository.getProductById(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SuccessResponse<Product>> call, Response<SuccessResponse<Product>> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    future.complete(response.body().getData());
                } else {
                    future.completeExceptionally(new Exception(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse<Product>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
