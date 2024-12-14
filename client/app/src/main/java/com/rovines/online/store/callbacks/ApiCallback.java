package com.rovines.online.store.callbacks;

import com.rovines.online.store.payload.api.ErrorResponse;
import com.rovines.online.store.payload.api.SuccessResponse;

import java.util.List;

public interface ApiCallback<T> {
    void onSuccess(SuccessResponse<T> successResponse);

    void onSuccess(SuccessResponse<List<T>> successResponse, Boolean fetch);

    void onError(ErrorResponse errorResponse);
}
