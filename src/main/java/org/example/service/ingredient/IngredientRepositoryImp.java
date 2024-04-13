package org.example.service.ingredient;
import org.example.model.Allergy;
import org.example.model.Ingredient;
import javax.persistence.EntityManager;

public class IngredientRepositoryImp implements IngredientRepository{

    EntityManager entityManager;

    public IngredientRepositoryImp(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void createIngredient(String name, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate) {
        entityManager.getTransaction().begin();
        Ingredient ingredient = new Ingredient(name, allergy, protein, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
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
    public void updateIngredient(Long ingredientId, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate) {
        entityManager.getTransaction().begin();
        Ingredient ingredient = entityManager.find(Ingredient.class, ingredientId);
        ingredient.setAllergy(allergy);
        ingredient.setProtein(protein);
        ingredient.setSodium(sodium);
        ingredient.setCalories(calories);
        ingredient.setTotalFat(totalFat);
        ingredient.setCholesterol(cholesterol);
        ingredient.setTotalCarbohydrate(totalCarbohydrate);
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
}
