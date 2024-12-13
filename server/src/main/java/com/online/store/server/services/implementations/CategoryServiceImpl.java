package com.online.store.server.services.implementations;

import com.online.store.server.exceptions.DuplicateElementException;
import com.online.store.server.exceptions.ResourceNotFoundException;
import com.online.store.server.models.Category;
import com.online.store.server.repositories.CategoryRepository;
import com.online.store.server.services.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                () -> new ResourceNotFoundException(String.format("Category with id %s not found", id))
        );
    }

    @Override
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new DuplicateElementException(String.format("Category with name %s already exists", category.getName()));
        }
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
