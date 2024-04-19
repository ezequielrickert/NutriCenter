package org.example.controller;

import org.example.model.Allergy;
import org.example.model.Ingredient;
import org.example.repository.ingredient.IngredientRepositoryImp;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IngredientController {

    IngredientRepositoryImp ingredientRepository;

    public IngredientController(EntityManager entityManager) {
        ingredientRepository = new IngredientRepositoryImp(entityManager);
    }

    public void createIngredient(String name, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientRepository.createIngredient(name, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
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

    public void updateIngredient(Long ingredientId, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientRepository.updateIngredient(ingredientId, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void deleteIngredient(Long ingredientId){
        ingredientRepository.deleteIngredient(ingredientId);
    }

    public List<Ingredient> getIngredientsOrderedByName(EntityManager entityManager) {
        return ingredientRepository.getAll();
    }

    public List<Ingredient> searchIngredientsByName(String searchTerm) {
        return ingredientRepository.searchIngredientsByName(searchTerm);
    }

    public Ingredient getIngredientByName(String ingredientName) {
        return ingredientRepository.getIngredientByName(ingredientName);
    }

    public List<Ingredient> getIngredientsBeginningWith(String beginning) {
        return ingredientRepository.getIngredientsBeginningWith(beginning);
    }
}
