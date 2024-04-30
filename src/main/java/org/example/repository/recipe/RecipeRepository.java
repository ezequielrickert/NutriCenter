package org.example.repository.recipe;

import org.example.model.Category;
import org.example.model.Ingredient;
import org.example.model.Recipe;

import java.util.List;

public interface RecipeRepository {

    public void addRecipe(String name, String description, List<Category> categoryList,
                          List<Ingredient> ingredientList, String username, Boolean isPublic);

    public Recipe getRecipe(Long recipeId);

    public void updateRecipe(Long recipeId, String name, String description, List<Category> categoryList,
                             List<Ingredient> ingredientList);

    public void deleteRecipe(Long recipeId);

    List<Recipe> getRecipesOrderedByName();

    List<Recipe> getPublicRecipesBeginningWith(String beginning);

    List<Recipe> getRecipeByUsername(String username);

    List<Recipe> getRecipeByIngredient(Long ingredientId);

    List<Recipe> getPublicRecipes(String recipe);

    Recipe getRecipeById(long recipeId);
}
