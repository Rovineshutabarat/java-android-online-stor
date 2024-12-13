package com.online.store.server.controllers.implementations;

import com.online.store.server.controllers.BaseController;
import com.online.store.server.payload.api.SuccessResponse;
import com.online.store.server.payload.request.ProductRequest;
import com.online.store.server.services.ProductService;
import com.online.store.server.utils.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController implements BaseController<ProductRequest, Integer> {
    private ProductService productService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success get all products.",
                productService.getAllProducts());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success get product by id.",
                productService.getProductById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.CREATED,
                "Success creating product.",
                productService.createProduct(productRequest));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@RequestBody @Valid ProductRequest productRequest, @PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success updating product.",
                productService.updateProduct(productRequest, id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success deleting product.",
                productService.deleteProduct(id));
    }
}
