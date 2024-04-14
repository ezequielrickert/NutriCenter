package org.example.controller;

import org.example.model.Allergy;
import org.example.model.Ingredient;
import org.example.service.ingredient.IngredientRepositoryImp;

import javax.persistence.EntityManager;

public class IngredientController {

    IngredientRepositoryImp ingredientRepository;

    public IngredientController(EntityManager entityManager) {
        ingredientRepository = new IngredientRepositoryImp(entityManager);
    }

    public void createIngredient(String name, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientRepository.createIngredient(name, allergy, protein, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void readIngredient(long ingredientId) {
        Ingredient ingredient = ingredientRepository.readIngredient(ingredientId);
        if (ingredient != null) {
            System.out.println("Ingredient ID: " + ingredient.getIngredientId());
            System.out.println("Ingredient Name: " + ingredient.getIngredientName());
        } else {
            System.out.println("Ingredient not found");
        }
    }

    public void updateIngredient(String ingredientName, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientRepository.updateIngredient(ingredientName, allergy, protein, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void deleteIngredient(String ingredientName){
        ingredientRepository.deleteIngredient(ingredientName);
    }
}
