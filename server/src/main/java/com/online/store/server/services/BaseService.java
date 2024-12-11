package com.online.store.server.services;

import java.util.List;

public interface BaseService<T, ID> {
    List<T> getAll();

    T getById(ID id);

    T create(T entity);

    T update(T entity, ID id);

    T delete(ID id);
}
