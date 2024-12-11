package com.online.store.server.services.implementations;

import com.online.store.server.models.Category;
import com.online.store.server.repositories.CategoryRepository;
import com.online.store.server.services.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements BaseService<Category, Integer> {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category, Integer id) {
        getById(id);
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Override
    public Category delete(Integer id) {
        Category category = getById(id);
        categoryRepository.delete(category);
        return category;
    }
}
