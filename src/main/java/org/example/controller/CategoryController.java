package org.example.controller;

import org.example.model.recipie.Category;
import org.example.service.CategoryService;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryController {

    EntityManager entityManager;

    CategoryService categoryService;

    public CategoryController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.categoryService = new CategoryService(entityManager);
    }

    public void createCategory(String categoryName) {
        categoryService.createCategory(categoryName);
    }

    public void readCategory(Long categoryId) {
        categoryService.readCategory(categoryId);
    }

    public void updateCategory(Long categoryId, String categoryName) {
        categoryService.updateCategory(categoryId, categoryName);
    }

    public void deleteCategory(Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    public List<Category> getCategoriesOrderedByName() {
        return categoryService.getCategoriesOrderedByName();
    }
}
