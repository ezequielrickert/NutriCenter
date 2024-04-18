package org.example.repository.ingredient;
import org.example.model.Allergy;
import org.example.model.Ingredient;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IngredientRepositoryImp implements IngredientRepository{

    EntityManager entityManager;

    public IngredientRepositoryImp(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void createIngredient(String name, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate) {
        entityManager.getTransaction().begin();
        Ingredient ingredient = new Ingredient(name, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
        entityManager.persist(ingredient);
        entityManager.getTransaction().commit();
    }

    @Override
    public Ingredient readIngredient(Long ingredientId) {
        entityManager.getTransaction().begin();
        Ingredient ingredient = entityManager.find(Ingredient.class, ingredientId);
        entityManager.getTransaction().commit();
        return ingredient;
    }

    @Override
    public void updateIngredient(Long ingredientID, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate) {
        entityManager.getTransaction().begin();
        Ingredient ingredient = entityManager.find(Ingredient.class, ingredientID);
        ingredient.setAllergy(allergy);
        ingredient.setProteins(proteins);
        ingredient.setSodium(sodium);
        ingredient.setCalories(calories);
        ingredient.setTotalFat(totalFat);
        ingredient.setCholesterol(cholesterol);
        ingredient.setTotalCarbohydrate(totalCarbohydrate);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        entityManager.getTransaction().begin();
        Ingredient ingredient = entityManager.find(Ingredient.class, ingredientId);
        if (ingredient != null) {
            entityManager.remove(ingredient);
        }
        entityManager.getTransaction().commit();
    }

    public List<Ingredient> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredient> cr = cb.createQuery(Ingredient.class);
        Root<Ingredient> root = cr.from(Ingredient.class);
        cr.select(root);

        Query query = entityManager.createQuery(cr);
        List<Ingredient> results = query.getResultList();
        return results;
    }
}