package org.example.service;

import org.example.model.Category;
import org.example.model.Ingredient;
import org.example.model.Recipe;
import org.example.repository.recipe.RecipeRepository;
import org.example.repository.recipe.RecipeRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public class RecipeService {

    RecipeRepository recipeRepository;
    EntityManager entityManager;

    public RecipeService(EntityManager entityManager) {
        this.recipeRepository = new RecipeRepositoryImpl(entityManager);
        this.entityManager = entityManager;
    }

    public void createRecipe(String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList, String username, Boolean isPublic) {
        recipeRepository.addRecipe(name, description, categoryList, ingredientList, username, isPublic);
    }

    public void updateRecipe(Long recipeId, String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList) {
        recipeRepository.updateRecipe(recipeId, name, description, categoryList, ingredientList);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteRecipe(recipeId);
    }

    public Recipe getRecipe(Long recipeId) {
        return recipeRepository.getRecipe(recipeId);
    }

    public List<Recipe> getRecipesOrderedByName() {
        return recipeRepository.getRecipesOrderedByName();
    }

    public List<Recipe> getRecipeByUsername(String username) {
        return recipeRepository.getRecipeByUsername(username);
    }
}
