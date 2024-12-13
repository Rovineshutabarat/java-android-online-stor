package com.online.store.server.services.implementations;

import com.online.store.server.exceptions.DuplicateElementException;
import com.online.store.server.exceptions.ResourceNotFoundException;
import com.online.store.server.models.Product;
import com.online.store.server.payload.request.ProductRequest;
import com.online.store.server.repositories.ProductRepository;
import com.online.store.server.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Product with id %s not found", id))
        );
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())) {
            throw new DuplicateElementException(String.format("Product with name %s already exists", productRequest.getName()));
        }
        return productRepository.save(
                Product.builder()
                        .name(productRequest.getName())
                        .description(productRequest.getDescription())
                        .price(productRequest.getPrice())
                        .image_url(productRequest.getImage_url())
                        .category(categoryService.getById(productRequest.getCategory_id()))
                        .build()
        );
    }

    @Override
    public Product updateProduct(ProductRequest productRequest, Integer id) {
        return productRepository.save(
                Product.builder()
                        .id(id)
                        .name(productRequest.getName())
                        .description(productRequest.getDescription())
                        .price(productRequest.getPrice())
                        .image_url(productRequest.getImage_url())
                        .category(categoryService.getById(productRequest.getCategory_id()))
                        .build()
        );
    }

    @Override
    public Product deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return product;
    }
}
