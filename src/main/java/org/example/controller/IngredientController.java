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

    public void updateIngredient(String ingredientName, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate){
        ingredientRepository.updateIngredient(ingredientName, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
    }

    public void deleteIngredient(String ingredientName){
        ingredientRepository.deleteIngredient(ingredientName);
    }

    public List<Ingredient> getIngredientsOrderedByName(EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredient> cq = cb.createQuery(Ingredient.class);
        Root<Ingredient> root = cq.from(Ingredient.class);
        cq.select(root);
        cq.orderBy(cb.asc(root.get("ingredientName")));
        return entityManager.createQuery(cq).getResultList();
    }
}
