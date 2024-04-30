package org.example.repository.ingredient;
import org.example.model.recipie.Ingredient;
import org.example.model.recipie.Allergy;

public interface IngredientRepository {

    void createIngredient(String name, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate);

    Ingredient readIngredient(Long ingredientId);

    void updateIngredient(Long ingredientID, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate);

    void deleteIngredient(Long ingredientId);
}
