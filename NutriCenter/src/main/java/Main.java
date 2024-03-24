import org.example.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // Create an EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClientPU");

        // Create an EntityManager
        EntityManager em = emf.createEntityManager();

        // Begin a transaction
        em.getTransaction().begin();

        // Create a new Client
        Client client = new Client("John Doe", "john.doe@example.com");
        Client client2 = new Client("John Doe Jr", "john.doejr@example.com");


        // Persist the Client
        em.persist(client);
        em.persist(client2);

        // Commit the transaction
        em.getTransaction().commit();

        // Close the EntityManager
        em.close();

        // Close the EntityManagerFactory
        emf.close();
    }
}

