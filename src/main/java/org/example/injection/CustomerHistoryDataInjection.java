package org.example.injection;

import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;
import org.example.model.recipe.Recipe;
import org.example.model.roles.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerHistoryDataInjection {

    // Define the recipe names used in previous examples
    private static final String[] RECIPE_NAMES = {
            "Avocado Salad",
            "Chicken Broccoli Stir-fry",
            "Peanut Butter Banana Smoothie",
            "Quinoa Salad",
            "Salmon with Almonds"
    };

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Retrieve all customers from the database
            List<Customer> customers = em.createQuery("SELECT c FROM CUSTOMER c", Customer.class)
                    .getResultList();

            // Iterate over each customer and create CustomerHistory with days
            for (Customer customer : customers) {
                List<Day> days = createAndPersistDays(em);

                // Create CustomerHistory for the current customer
                CustomerHistory customerHistory = new CustomerHistory();
                customer.setCustomerHistory(customerHistory);
                customerHistory.setDays(days);
                em.persist(customerHistory);

                // Set the customer's history after persisting
                customer.setCustomerHistory(customerHistory);
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

    private static List<Day> createAndPersistDays(EntityManager em) {
        List<Day> days = new ArrayList<>();

        for (int i = 0; i <= 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // Create a new Day entity with the current date and recipes
            Day day = new Day(dayOfWeek, date);
            day.setBreakfast(getRecipeByName(em, "Avocado Salad"));
            day.setLunch(getRecipeByName(em, "Chicken Broccoli Stir-fry"));
            day.setDinner(getRecipeByName(em, "Peanut Butter Banana Smoothie"));

            // Persist the Day entity
            em.persist(day);
            days.add(day);
        }

        return days;
    }

    private static Recipe getRecipeByName(EntityManager em, String recipeName) {
        return em.createQuery("SELECT r FROM RECIPE r WHERE r.recipeName = :name", Recipe.class)
                .setParameter("name", recipeName)
                .getSingleResult();
    }
}
