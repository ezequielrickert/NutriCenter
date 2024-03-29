package org.example;

import org.example.model.Customer;
import org.example.model.Nutritionist;
import org.example.model.Store;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateUsersTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    @BeforeEach
    public void setup() {
        // Create an EntityManagerFactory
        emf = Persistence.createEntityManagerFactory("UserPU");

        // Create an EntityManager
        em = emf.createEntityManager();

        // Begin a transaction
        em.getTransaction().begin();
    }

    @AfterEach
    public void teardown() {
        // Commit the transaction
        em.getTransaction().commit();

        // Close the EntityManager
        em.close();

        // Close the EntityManagerFactory
        emf.close();
    }

    @Test
    @Order(1)
    public void createClient() {


        // Create a new Client
        Customer ezequiel = new Customer("Ezequiel Rickert", "erickert@mail.austral.edu.ar");
        Customer tomas = new Customer("Tomas Bernardez", "tbernardez@mail.austral.edu.ar");
        Customer hilario = new Customer("Hilario Lagos", "hlagos@mail.austral.edu.ar");

        // Persist the Client
        em.persist(ezequiel);
        em.persist(tomas);
        em.persist(hilario);


    }


    @Test
    @Order(1)
    public void createStore(){


        // Create a new Client
        Store newGarden = new Store("New Garden", "newgarden@gmail.com","1144445555");
        Store greenFood = new Store("Green Food", "greenfood@gmail.com", "1122223333");

        // Persist the Client
        em.persist(newGarden);
        em.persist(greenFood);


    }


    @Test
    @Order(1)
    public void createNutritionist(){


        // Create a new Client
        Nutritionist yael = new Nutritionist("yael test", "yael@gmail.com","UBA 4 year diploma");
        Nutritionist paola = new Nutritionist("paola test", "paola@gmail.com", "UA 4 year diploma");

        // Persist the Client
        em.persist(yael);
        em.persist(paola);


    }
}
