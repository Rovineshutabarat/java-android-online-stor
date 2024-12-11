package com.online.store.server.controllers;

import com.online.store.server.payload.api.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface BaseController<T, ID> {
    ResponseEntity<SuccessResponse> getAll();

    ResponseEntity<SuccessResponse> getById(ID id);

    ResponseEntity<SuccessResponse> create(T entity);

    ResponseEntity<SuccessResponse> update(T entity, ID id);

    ResponseEntity<SuccessResponse> delete(ID id);
}
