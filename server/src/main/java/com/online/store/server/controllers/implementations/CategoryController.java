package com.online.store.server.controllers.implementations;

import com.online.store.server.controllers.BaseController;
import com.online.store.server.models.Category;
import com.online.store.server.payload.api.SuccessResponse;
import com.online.store.server.services.implementations.CategoryServiceImpl;
import com.online.store.server.utils.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController implements BaseController<Category, Integer> {
    private final CategoryServiceImpl categoryService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success get all categories.",
                categoryService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success get category by id.",
                categoryService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse> create(@RequestBody Category category) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.CREATED,
                "Success creating category.",
                categoryService.create(category));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@RequestBody Category category, @PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success updating category.",
                categoryService.update(category, id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(HttpStatus.OK,
                "Success deleting category.",
                categoryService.delete(id));
    }
}
