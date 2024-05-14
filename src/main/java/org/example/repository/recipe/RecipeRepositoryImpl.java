package org.example.repository.recipe;

import org.example.model.Category;
import org.example.model.Ingredient;
import org.example.model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepository{

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
    public List<Recipe> getPublicRecipesBeginningWith(String beginning) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> root = cr.from(Recipe.class);
        Predicate nameLike = cb.like(root.get("recipeName"), beginning + "%");
        Predicate isPublic = cb.equal(root.get("isPublic"), true);
        cr.select(root).where(cb.and(nameLike, isPublic)).orderBy(cb.asc(root.get("recipeName")));

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
    public Recipe getRecipeById(long recipeId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cr = cb.createQuery(Recipe.class);
        Root<Recipe> root = cr.from(Recipe.class);
        cr.select(root).where(cb.equal(root.get("recipeId"), recipeId));

        Query query = entityManager.createQuery(cr);
        return (Recipe) query.getSingleResult();
    }
}
