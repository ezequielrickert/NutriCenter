package org.example.controller;

import org.example.model.Allergy;
import org.example.model.Ingredient;
import org.example.repository.ingredient.IngredientRepositoryImp;
import org.example.service.IngredientService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IngredientController {

    IngredientService ingredientService;

    public IngredientController(EntityManager entityManager) {
        ingredientService = new IngredientService(entityManager);
    }

    public void createIngredient(String name, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientService.createIngredient(name, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void readIngredient(long ingredientId) {
        Ingredient ingredient = ingredientService.readIngredient(ingredientId);
        if (ingredient != null) {
            System.out.println("Ingredient ID: " + ingredient.getIngredientId());
            System.out.println("Ingredient Name: " + ingredient.getIngredientName());
        } else {
            System.out.println("Ingredient not found");
        }
    }

    public void updateIngredient(Long ingredientId, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientService.updateIngredient(ingredientId, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void deleteIngredient(Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
    }

    public List<Ingredient> getIngredientsOrderedByName() {
        List<Ingredient> result = ingredientService.getAll();
        return result;
    }

    public List<Ingredient> searchIngredientsByName(String searchTerm) {
        return ingredientService.searchIngredientsByName(searchTerm);
    }

    public Ingredient getIngredientByName(String ingredientName) {
        return ingredientService.getIngredientByName(ingredientName);
    }

    public List<Ingredient> getIngredientsBeginningWith(String beginning) {
        return ingredientService.getIngredientsBeginningWith(beginning);
    }
}
