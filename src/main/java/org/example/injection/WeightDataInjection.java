package org.example.injection;

import org.example.model.history.WeightHistory;
import org.example.model.roles.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class WeightDataInjection {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Retrieve Customers from the database
            List<Customer> customers = em.createQuery("SELECT c FROM CUSTOMER c", Customer.class)
                    .getResultList();

            for (Customer customer : customers) {
                // Create and persist WeightHistory instances for each customer
                WeightHistory weightHistory3 = new WeightHistory(72.0); // Example weight
                weightHistory3.setCustomer(customer);
                weightHistory3.setDate(LocalDate.now().minusDays(2));
                em.persist(weightHistory3);

                WeightHistory weightHistory2 = new WeightHistory(68.2); // Example weight
                weightHistory2.setCustomer(customer);
                weightHistory2.setDate(LocalDate.now().minusDays(1));
                em.persist(weightHistory2);

                WeightHistory weightHistory1 = new WeightHistory(70.5);
                weightHistory1.setDate(LocalDate.now()); // Example weight
                weightHistory1.setCustomer(customer);
                em.persist(weightHistory1);
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
