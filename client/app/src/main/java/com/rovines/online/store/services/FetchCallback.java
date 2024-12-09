package com.rovines.online.store.services;

import com.rovines.online.store.models.Category;

import java.util.List;

public interface FetchCallback<T> {
    void onSuccess(List<T> entity);

    void onError(Throwable throwable);
}
