package org.example;

import org.example.model.recipe.Allergy;
import org.example.model.recipe.Category;
import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RecipeDataInjection {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Creating and persisting Category instances
            Category category1 = new Category();
            category1.setCategoryName("Vegetarian");
            em.persist(category1);

            Category category2 = new Category();
            category2.setCategoryName("Vegan");
            em.persist(category2);

            Category category3 = new Category();
            category3.setCategoryName("Gluten-Free");
            em.persist(category3);

            Category category4 = new Category();
            category3.setCategoryName("Omnivore");
            em.persist(category4);

            // Creating and persisting Allergy instances
            Allergy allergy1 = new Allergy("Peanuts", "Common allergy to peanuts.");
            em.persist(allergy1);

            Allergy allergy2 = new Allergy("Shellfish", "Allergy to shellfish including shrimp, crabs, and lobsters.");
            em.persist(allergy2);

            Allergy allergy3 = new Allergy("Dairy", "Allergy to dairy products.");
            em.persist(allergy3);

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
