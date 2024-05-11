package org.example.service;

import org.example.model.recipe.Allergy;
import org.example.model.recipe.Ingredient;
import org.example.model.recipe.Recipe;
import org.example.repository.ingredient.IngredientRepositoryImp;
import org.example.repository.recipe.RecipeRepository;
import org.example.repository.recipe.RecipeRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public class IngredientService {

    EntityManager entityManager;
    IngredientRepositoryImp ingredientRepository;
    RecipeRepository recipeRepository;

    public IngredientService(EntityManager entityManager) {
        entityManager = entityManager;
        ingredientRepository = new IngredientRepositoryImp(entityManager);
        recipeRepository = new RecipeRepositoryImpl(entityManager);
    }

    public void createIngredient(String name, Allergy allergy, int proteins, int sodium, int calories, int totalFat,
                              int cholesterol, int totalCarbohydrate) {
        ingredientRepository.createIngredient(name, allergy, proteins, sodium, calories, totalFat,
                cholesterol, totalCarbohydrate);
    }

    public Ingredient readIngredient(Long ingredientId) {
        return ingredientRepository.readIngredient(ingredientId);
    }

    public void updateIngredient(Long ingredientID, Allergy allergy, int proteins, int sodium, int calories,
                                 int totalFat, int cholesterol, int totalCarbohydrate) {
        ingredientRepository.updateIngredient(ingredientID, allergy, proteins, sodium,
                calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void deleteIngredient(Long ingredientId) {
        List<Recipe> ingredientList = recipeRepository.getRecipeByIngredient(ingredientId);
        if(ingredientList != null) {
            for (Recipe recipe : ingredientList) {
                recipeRepository.deleteRecipe(recipe.getRecipeId());
            }
        }
        ingredientRepository.deleteIngredient(ingredientId);
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.getAll();
    }


    public List<Ingredient> getIngredientsBeginningWith(String beginning) {
        return ingredientRepository.getIngredientsBeginningWith(beginning);
    }

    public List<Ingredient> searchIngredientsByName(String searchTerm) {
        return ingredientRepository.searchIngredientsByName(searchTerm);
    }


    public Ingredient getIngredientByName(String ingredientName) {
        return ingredientRepository.getIngredientByName(ingredientName);
    }
}
