package com.rovines.online.store.helpers;

import com.rovines.online.store.repositories.CategoryRepository;
import com.rovines.online.store.repositories.ProductRepository;
import com.rovines.online.store.repositories.UserRepository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String API_URL = "https://dbde-103-179-248-238.ngrok-free.app/";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ProductRepository getProductRepository() {
        return getInstance().create(ProductRepository.class);
    }

    public static CategoryRepository getCategoryRepository() {
        return getInstance().create(CategoryRepository.class);
    }

    public static UserRepository getUserRepository() {
        return getInstance().create(UserRepository.class);
    }
}
