package org.example.repository.recipe;

import org.example.model.recipe.Category;
import org.example.model.recipe.Ingredient;
import org.example.model.recipe.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepository {

    EntityManager entityManager;

    public RecipeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addRecipe(String name, String description, List<Category> categoryList,
                          List<Ingredient> ingredientList, String username, Boolean isPublic) {
        entityManager.getTransaction().begin();
        Recipe recipe = new Recipe();
        recipe.setRecipeName(name);
        recipe.setRecipeDescription(description);
        recipe.setCategoryList(categoryList);
        recipe.setIngredientList(ingredientList);
        recipe.setRecipeUsername(username);
        recipe.setIsPublic(isPublic);
        entityManager.persist(recipe);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateRecipe(Long recipeId, String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList, Boolean isPublic) {
        entityManager.getTransaction().begin();
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        String username = recipe.getRecipeUsername();
        recipe.setRecipeName(name);
        recipe.setRecipeDescription(description);
        recipe.setCategoryList(categoryList);
        recipe.setIngredientList(ingredientList);
        recipe.setIsPublic(isPublic);
        recipe.setRecipeUsername(username);
        entityManager.persist(recipe);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        entityManager.getTransaction().begin();
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        entityManager.remove(recipe);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Recipe> getRecipesOrderedByName() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> root = cr.from(Recipe.class);
        cr.select(root);

        Query query = entityManager.createQuery(cr);
        List<Recipe> results = query.getResultList();
        return results;
    }

    @Override
    public List<Recipe> getRecipeByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> root = cr.from(Recipe.class);
        cr.select(root).where(cb.equal(root.get("recipeUsername"), username));

        Query query = entityManager.createQuery(cr);
        List<Recipe> results = query.getResultList();
        return results;
    }

    @Override
    public List<Recipe> getRecipeByIngredient(Long ingredientId) {
        String jpql = "SELECT r FROM RECIPE r JOIN r.ingredientList i WHERE i.id = :ingredientId";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        query.setParameter("ingredientId", ingredientId);
        List<Recipe> results = query.getResultList();
        return results;
    }

    @Override
    public List<Recipe> getPublicRecipes(String recipe) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> root = cr.from(Recipe.class);
        Predicate nameLike = cb.like(cb.lower(root.get("recipeName")), "%" + recipe + "%");
        Predicate isPublic = cb.equal(root.get("isPublic"), true);
        cr.select(root).where(cb.and(nameLike, isPublic));

        Query query = entityManager.createQuery(cr);
        List<Recipe> results = query.getResultList();
        return results;
    }

    @Override
    public List<Recipe> getPublicRecipesByDietType(String dietType, String searchTerm) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = cr.from(Recipe.class);
        Join<Recipe, Category> categoryJoin = recipeRoot.join("categoryList");

        Predicate nameLike = cb.like(cb.lower(recipeRoot.get("recipeName")), "%" + searchTerm.toLowerCase() + "%");
        Predicate isPublic = cb.equal(recipeRoot.get("isPublic"), true);
        Predicate dietTypeLike = cb.like(cb.lower(categoryJoin.get("categoryName")), "%" + dietType.toLowerCase() + "%");

        cr.select(recipeRoot).where(cb.and(nameLike, isPublic, dietTypeLike)).distinct(true);

        TypedQuery<Recipe> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

    @Override
    public Recipe getRecipeById(long recipeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> root = cr.from(Recipe.class);
        cr.select(root).where(cb.equal(root.get("recipeId"), recipeId));

        Query query = entityManager.createQuery(cr);
        return (Recipe) query.getSingleResult();
    }
}
