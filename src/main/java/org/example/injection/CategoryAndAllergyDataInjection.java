package org.example.injection;

import org.example.model.recipe.Allergy;
import org.example.model.recipe.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CategoryAndAllergyDataInjection {
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
            category4.setCategoryName("Omnivore");
            em.persist(category4);

            Category category5 = new Category();
            category5.setCategoryName("Keto");
            em.persist(category5);

            Category category6 = new Category();
            category6.setCategoryName("Paleo");
            em.persist(category6);

            Category category7 = new Category();
            category7.setCategoryName("Pescatarian");
            em.persist(category7);

            Category category8 = new Category();
            category8.setCategoryName("Mediterranean");
            em.persist(category8);

            // Creating and persisting Allergy instances
            Allergy allergy1 = new Allergy("Peanuts", "Common allergy to peanuts.");
            em.persist(allergy1);

            Allergy allergy2 = new Allergy("Shellfish", "Allergy to shellfish including shrimp, crabs, and lobsters.");
            em.persist(allergy2);

            Allergy allergy3 = new Allergy("Dairy", "Allergy to dairy products.");
            em.persist(allergy3);

            Allergy allergy4 = new Allergy("Gluten", "Allergy to gluten found in wheat, barley, and rye.");
            em.persist(allergy4);

            Allergy allergy5 = new Allergy("Soy", "Allergy to soy products.");
            em.persist(allergy5);

            Allergy allergy6 = new Allergy("Tree nuts", "Allergy to tree nuts such as almonds, cashews, and walnuts.");
            em.persist(allergy6);

            Allergy allergy7 = new Allergy("Eggs", "Allergy to egg proteins.");
            em.persist(allergy7);

            Allergy allergy8 = new Allergy("Fish", "Allergy to fish proteins.");
            em.persist(allergy8);

            Allergy allergy9 = new Allergy("None", "No allergy");
            em.persist(allergy9);

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
