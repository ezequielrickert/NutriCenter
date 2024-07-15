package org.example.injection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;
import org.example.model.roles.Store;
import org.example.model.roles.SuperAdmin;

public class UserDataInjection {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Example of creating and persisting Customers
            Customer customer1 = new Customer();
            customer1.setCustomerName("AliceJohnson");
            customer1.setCustomerEmail("alice.johnson@example.com");
            customer1.setCustomerPassword("password123");
            em.persist(customer1);

            Customer customer2 = new Customer();
            customer2.setCustomerName("BobSmith");
            customer2.setCustomerEmail("bob.smith@example.com");
            customer2.setCustomerPassword("password456");
            em.persist(customer2);

            Customer customer3 = new Customer();
            customer3.setCustomerName("CharlieDavis");
            customer3.setCustomerEmail("charlie.davis@example.com");
            customer3.setCustomerPassword("password789");
            em.persist(customer3);

            Customer customer4 = new Customer();
            customer4.setCustomerName("DianaAdams");
            customer4.setCustomerEmail("diana.adams@example.com");
            customer4.setCustomerPassword("password012");
            em.persist(customer4);

            Customer customer5 = new Customer();
            customer5.setCustomerName("EvanBrown");
            customer5.setCustomerEmail("evan.brown@example.com");
            customer5.setCustomerPassword("password345");
            em.persist(customer5);

            // Example of creating and persisting Nutritionists
            Nutritionist nutritionist1 = new Nutritionist();
            nutritionist1.setNutritionistName("LisaRay");
            nutritionist1.setNutritionistEmail("lisa.ray@example.com");
            nutritionist1.setNutritionistPassword("secure123");
            nutritionist1.setEducationDiploma("Nutrition Science");
            em.persist(nutritionist1);

            Nutritionist nutritionist2 = new Nutritionist();
            nutritionist2.setNutritionistName("MarkNeal");
            nutritionist2.setNutritionistEmail("mark.neal@example.com");
            nutritionist2.setNutritionistPassword("password234");
            nutritionist2.setEducationDiploma("Dietetics");
            em.persist(nutritionist2);

            Nutritionist nutritionist3 = new Nutritionist();
            nutritionist3.setNutritionistName("NancyDrew");
            nutritionist3.setNutritionistEmail("nancy.drew@example.com");
            nutritionist3.setNutritionistPassword("mystery789");
            nutritionist3.setEducationDiploma("Food and Nutrition");
            em.persist(nutritionist3);

            Nutritionist nutritionist4 = new Nutritionist();
            nutritionist4.setNutritionistName("OscarWilde");
            nutritionist4.setNutritionistEmail("oscar.wilde@example.com");
            nutritionist4.setNutritionistPassword("dorian123");
            nutritionist4.setEducationDiploma("Clinical Nutrition");
            em.persist(nutritionist4);

            Nutritionist nutritionist5 = new Nutritionist();
            nutritionist5.setNutritionistName("PamBeesly");
            nutritionist5.setNutritionistEmail("pam.beesly@example.com");
            nutritionist5.setNutritionistPassword("artlove456");
            nutritionist5.setEducationDiploma("Public Health Nutrition");
            em.persist(nutritionist5);

            // Example of creating and persisting Stores
            Store store1 = new Store();
            store1.setStoreName("HealthyFoods");
            store1.setStoreMail("contact@healthyfoods.com");
            store1.setStorePassword("healthy123");
            store1.setStorePhoneNumber("123-456-7890");
            em.persist(store1);

            Store store2 = new Store();
            store2.setStoreName("GreenGroceries");
            store2.setStoreMail("support@greengroceries.com");
            store2.setStorePassword("green456");
            store2.setStorePhoneNumber("098-765-4321");
            em.persist(store2);

            Store store3 = new Store();
            store3.setStoreName("OrganicPicks");
            store3.setStoreMail("info@organicpicks.com");
            store3.setStorePassword("organic789");
            store3.setStorePhoneNumber("456-123-7890");
            em.persist(store3);

            Store store4 = new Store();
            store4.setStoreName("FarmFresh");
            store4.setStoreMail("hello@farmfresh.com");
            store4.setStorePassword("farm012");
            store4.setStorePhoneNumber("321-654-9870");
            em.persist(store4);

            Store store5 = new Store();
            store5.setStoreName("VeganDelights");
            store5.setStoreMail("contact@vegandelights.com");
            store5.setStorePassword("vegan345");
            store5.setStorePhoneNumber("654-789-1230");
            em.persist(store5);

            // Example of creating and persisting a SuperAdmin
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setAdminUsername("admin");
            superAdmin.setAdminPassword("admin");
            em.persist(superAdmin);

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
