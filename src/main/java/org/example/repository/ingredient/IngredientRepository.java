package org.example.repository.ingredient;
import org.example.model.recipe.Ingredient;
import org.example.model.recipe.Allergy;

public interface IngredientRepository {

    void createIngredient(String name, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate);

    Ingredient readIngredient(Long ingredientId);

    void updateIngredient(Long ingredientID, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate);

    void deleteIngredient(Long ingredientId);
}
