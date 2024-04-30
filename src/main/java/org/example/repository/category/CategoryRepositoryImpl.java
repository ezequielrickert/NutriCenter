package org.example.repository.category;

import org.example.model.recipie.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CategoryRepositoryImpl implements CateogryRepository{

    EntityManager entityManager;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createCategory(String categoryName) {
        entityManager.getTransaction().begin();
        Category category = new Category();
        category.setCategoryName(categoryName);
        entityManager.persist(category);
        entityManager.getTransaction().commit();
    }

    @Override
    public Category readCategory(Long categoryId) {
        entityManager.getTransaction().begin();
        Category category = entityManager.find(Category.class, categoryId);
        entityManager.getTransaction().commit();
        return category;
    }

    @Override
    public void updateCategory(Long categoryId, String categoryName) {
        entityManager.getTransaction().begin();
        Category category = entityManager.find(Category.class, categoryId);
        category.setCategoryName(categoryName);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        entityManager.getTransaction().begin();
        Category category = entityManager.find(Category.class, categoryId);
        entityManager.remove(category);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Category> getCategoriesOrderedByName() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> cr = cb.createQuery(Category.class);
        Root<Category> root = cr.from(Category.class);
        cr.select(root);

        Query query = entityManager.createQuery(cr);
        List<Category> results = query.getResultList();
        return results;
    }
}
