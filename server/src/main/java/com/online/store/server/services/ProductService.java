package com.online.store.server.services;

import com.online.store.server.models.Product;
import com.online.store.server.payload.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Integer id);

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(ProductRequest productRequest, Integer id);

    Product deleteProduct(Integer id);
}
