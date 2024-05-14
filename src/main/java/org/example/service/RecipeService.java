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
                             List<Ingredient> ingredientList, Boolean isPublic) {
        recipeRepository.updateRecipe(recipeId, name, description, categoryList, ingredientList, isPublic);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteRecipe(recipeId);
    }

    public List<Recipe> getRecipesOrderedByName() {
        return recipeRepository.getRecipesOrderedByName();
    }

    public List<Recipe> getRecipeByUsername(String username) {
        return recipeRepository.getRecipeByUsername(username);
    }

    public List<Recipe> getPublicRecipesBeginningWith(String beginning) { return recipeRepository.getPublicRecipesBeginningWith(beginning); }

    public List<Recipe> getPublicRecipes(String recipe) { return recipeRepository.getPublicRecipes(recipe); }

    public Recipe getRecipeById(long recipeId) { return recipeRepository.getRecipeById(recipeId); }
}
