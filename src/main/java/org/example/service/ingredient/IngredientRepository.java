package org.example.service.ingredient;
import org.example.model.Ingredient;
import org.example.model.Allergy;

public interface IngredientRepository {

    void createIngredient(String name, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate);

    Ingredient readIngredient(Long ingredientId);

    void updateIngredient(String ingredientName, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate);

    void deleteIngredient(String ingredientName);
}
