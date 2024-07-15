package org.example.injection;

import org.example.model.recipe.Allergy;
import org.example.model.recipe.Ingredient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class IngredientDataInjection {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Retrieve Allergy instances from the database
            Map<String, Allergy> allergies = new HashMap<>();
            allergies.put("Peanuts", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Peanuts")
                    .getSingleResult());
            allergies.put("Shellfish", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Shellfish")
                    .getSingleResult());
            allergies.put("Dairy", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Dairy")
                    .getSingleResult());
            allergies.put("Gluten", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Gluten")
                    .getSingleResult());
            allergies.put("Soy", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Soy")
                    .getSingleResult());
            allergies.put("Tree nuts", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Tree nuts")
                    .getSingleResult());
            allergies.put("Eggs", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Eggs")
                    .getSingleResult());
            allergies.put("Fish", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "Fish")
                    .getSingleResult());
            allergies.put("None", em.createQuery("SELECT a FROM ALLERGY a WHERE a.allergyName = :name", Allergy.class)
                    .setParameter("name", "None")
                    .getSingleResult());

            // Create Ingredient instances
            Ingredient[] ingredients = new Ingredient[] {
                    new Ingredient("Almond", allergies.get("Tree nuts"), 21, 1, 575, 50, 0, 22),
                    new Ingredient("Salmon", allergies.get("Fish"), 20, 59, 208, 13, 55, 0),
                    new Ingredient("Milk", allergies.get("Dairy"), 3, 42, 42, 1, 5, 5),
                    new Ingredient("Peanut Butter", allergies.get("Peanuts"), 25, 397, 588, 50, 0, 20),
                    new Ingredient("Soy Sauce", allergies.get("Soy"), 8, 5586, 53, 0, 0, 8),
                    new Ingredient("Egg", allergies.get("Eggs"), 13, 124, 155, 11, 373, 1),
                    new Ingredient("Shrimp", allergies.get("Shellfish"), 24, 111, 99, 1, 152, 1),
                    new Ingredient("Bread", allergies.get("Gluten"), 9, 491, 265, 3, 0, 49),
                    new Ingredient("Avocado", allergies.get("None"), 2, 7, 160, 15, 0, 9),
                    new Ingredient("Chicken Breast", allergies.get("None"), 31, 74, 165, 3, 85, 0),
                    new Ingredient("Broccoli", allergies.get("None"), 3, 33, 34, 0, 0, 7),
                    new Ingredient("Cheddar Cheese", allergies.get("Dairy"), 25, 621, 402, 33, 105, 1),
                    new Ingredient("Tuna", allergies.get("Fish"), 29, 50, 132, 1, 60, 0),
                    new Ingredient("Apple", allergies.get("None"), 0, 1, 52, 0, 0, 14),
                    new Ingredient("Quinoa", allergies.get("Gluten"), 4, 7, 120, 2, 0, 21),
                    new Ingredient("Yogurt", allergies.get("Dairy"), 10, 36, 59, 0, 5, 3),
                    new Ingredient("Cashew", allergies.get("Tree nuts"), 18, 12, 553, 44, 0, 30),
                    new Ingredient("Lentils", allergies.get("None"), 9, 6, 116, 0, 0, 20),
                    new Ingredient("Oats", allergies.get("Gluten"), 17, 2, 389, 7, 0, 66),
                    new Ingredient("Carrot", allergies.get("None"), 1, 69, 41, 0, 0, 10)
            };

            // Persist Ingredient instances
            for (Ingredient ingredient : ingredients) {
                em.persist(ingredient);
            }

            // Commit the transaction
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            emf.close();
        }
    }
}
