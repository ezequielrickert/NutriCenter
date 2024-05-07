package org.example.repository.recipe;

import org.example.model.recipie.Category;
import org.example.model.recipie.Ingredient;
import org.example.model.recipie.Recipe;

import java.util.List;

public interface RecipeRepository {

    void addRecipe(String name, String description, List<Category> categoryList,
                          List<Ingredient> ingredientList, String username, Boolean isPublic);

    void updateRecipe(Long recipeId, String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList, Boolean isPublic);

    void deleteRecipe(Long recipeId);

    List<Recipe> getRecipesOrderedByName();

    List<Recipe> getPublicRecipesBeginningWith(String beginning);

    List<Recipe> getRecipeByUsername(String username);

    List<Recipe> getRecipeByIngredient(Long ingredientId);

    List<Recipe> getPublicRecipes(String recipe);

    Recipe getRecipeById(long recipeId);
}
