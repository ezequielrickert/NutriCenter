package org.example.controller;

import org.example.model.recipie.Category;
import org.example.model.recipie.Ingredient;
import org.example.model.recipie.Recipe;
import org.example.service.RecipeService;

import javax.persistence.EntityManager;
import java.util.List;

public class RecipeController {

    EntityManager entityManager;

    RecipeService recipeService;

    public RecipeController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.recipeService = new RecipeService(entityManager);
    }

    public void createRecipe(String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList, String username, Boolean isPublic) {
        recipeService.createRecipe(name, description, categoryList, ingredientList, username, isPublic);
    }

    public void updateRecipe(Long recipeId, String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList, Boolean isPublic) {
        recipeService.updateRecipe(recipeId, name, description, categoryList, ingredientList, isPublic);
    }

    public void deleteRecipe(Long recipeId) {
        recipeService.deleteRecipe(recipeId);
    }

    public List<Recipe> getRecipesOrderedByName() {
        return recipeService.getRecipesOrderedByName();
    }

    public List<Recipe> getRecipeByUsername(String username) {
        return recipeService.getRecipeByUsername(username);
    }

    public List<Recipe> getPublicRecipesBeginningWith(String beginning) {return recipeService.getPublicRecipesBeginningWith(beginning); }

    public List<Recipe> getPublicRecipes(String recipe) { return recipeService.getPublicRecipes(recipe); }

    public Recipe getRecipeById(long recipeId) { return recipeService.getRecipeById(recipeId); }
}
