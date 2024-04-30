package org.example.service;
import org.example.model.Category;
import org.example.repository.category.CategoryRepositoryImpl;
import org.example.repository.category.CateogryRepository;
import javax.persistence.EntityManager;
import java.util.List;

public class CategoryService {

    EntityManager entityManager;

    CateogryRepository cateogryRepository;

    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cateogryRepository = new CategoryRepositoryImpl(entityManager);
    }

    public void createCategory(String categoryName) {
        cateogryRepository.createCategory(categoryName);
    }

    public void readCategory(Long categoryId) {
        cateogryRepository.readCategory(categoryId);
    }

    public void updateCategory(Long categoryId, String categoryName) {
        cateogryRepository.updateCategory(categoryId, categoryName);
    }

    public void deleteCategory(Long categoryId) {
        cateogryRepository.deleteCategory(categoryId);
    }

    public List<Category> getCategoriesOrderedByName() {
        return cateogryRepository.getCategoriesOrderedByName();
    }
}
