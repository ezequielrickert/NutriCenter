package org.example.repository.category;

import org.example.model.recipe.Category;

import java.util.List;

public interface CateogryRepository {

    void createCategory(String categoryName);

    Category readCategory(Long categoryId);

    void updateCategory(Long categoryId, String categoryName);

    void deleteCategory(Long categoryId);

    List<Category> getCategoriesOrderedByName();

}
