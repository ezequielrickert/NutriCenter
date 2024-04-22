package org.example.repository.recipe;

import org.example.model.Category;
import org.example.model.Ingredient;
import org.example.model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        recipe.setUsername(username);
        recipe.setIsPublic(isPublic);
        entityManager.persist(recipe);
        entityManager.getTransaction().commit();
    }

    @Override
    public Recipe getRecipe(Long recipeId) {
        entityManager.getTransaction().begin();
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        entityManager.getTransaction().commit();
        return recipe;
    }

    @Override
    public void updateRecipe(Long recipeId, String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList) {
        entityManager.getTransaction().begin();
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        String username = recipe.getUsername();
        Boolean isPublic = recipe.getIsPublic();
        recipe.setRecipeName(name);
        recipe.setRecipeDescription(description);
        recipe.setCategoryList(categoryList);
        recipe.setIngredientList(ingredientList);
        recipe.setIsPublic(isPublic);
        recipe.setUsername(username);
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
        cr.select(root).where(cb.equal(root.get("username"), username));

        Query query = entityManager.createQuery(cr);
        List<Recipe> results = query.getResultList();
        return results;
    }
}
