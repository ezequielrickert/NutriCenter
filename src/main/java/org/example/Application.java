package org.example;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.example.controller.CustomerController;
import org.example.controller.NutritionistController;
import org.example.controller.StoreController;
import org.example.model.Customer;
import org.example.model.Nutritionist;
import org.example.model.Store;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Application {

  private static final Gson gson = new Gson();
  public static void main(String[] args) {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

    Spark.port(8080);

    storedCustomer(entityManagerFactory.createEntityManager());

    storedNutritionist(entityManagerFactory.createEntityManager());

    storedStore(entityManagerFactory.createEntityManager());

    /* Dynamic Content from Db */
    Spark.get("/persisted-customers/:id",
            (req, resp) -> {
              final String id = req.params("id");

              /* Business Logic */
              final EntityManager entityManager = entityManagerFactory.createEntityManager();
              final EntityTransaction tx = entityManager.getTransaction();
              tx.begin();
              Customer customer = entityManager.find(Customer.class, Long.valueOf(id));
              tx.commit();
              entityManager.close();

              resp.type("application/json");
              return customer.asJson();
            }
    );

    Spark.get("/persisted-nutritionist/:id",
            (req, resp) -> {
              final String id = req.params("id");

              /* Business Logic */
              final EntityManager entityManager = entityManagerFactory.createEntityManager();
              final EntityTransaction tx = entityManager.getTransaction();
              tx.begin();
              Nutritionist nutritionist = entityManager.find(Nutritionist.class, Long.valueOf(id));
              tx.commit();
              entityManager.close();

              resp.type("application/json");
              return nutritionist.asJson();
            }
    );

    Spark.get("/persisted-store/:id",
            (req, resp) -> {
              final String id = req.params("id");

              /* Business Logic */
              final EntityManager entityManager = entityManagerFactory.createEntityManager();
              final EntityTransaction tx = entityManager.getTransaction();
              tx.begin();
              Store store = entityManager.find(Store.class, Long.valueOf(id));
              tx.commit();
              entityManager.close();

              resp.type("application/json");
              return store.asJson();
            }
    );

  }

  private static void storedCustomer(EntityManager entityManager) {
    CustomerController customerController = new CustomerController(entityManager);
    customerController.createClient("erickert", "erickert@mail.austral.edu.ar");
    customerController.createClient("hlagos", "hlagos@mail.austral.edu.ar");
    customerController.createClient("tbernardez", "tbernardez@mail.austral.edu.ar");
  }

  private static void storedNutritionist(EntityManager entityManager){
    NutritionistController nutritionistController = new NutritionistController(entityManager);
    nutritionistController.createNutritionist("yael", "yael@test.com", "UA 4 year diploma");
    nutritionistController.createNutritionist("paola", "paola@test.com", "UBA 4 year diploma");
  }

  private static void storedStore(EntityManager entityManager){
    StoreController storeController = new StoreController(entityManager);
    storeController.createStore("New Garden", "newgarden@gmail.com", "1122223333");
    storeController.createStore("Green Food", "greenfood@gmail.com", "1133334444");
    storeController.createStore("Green Life", "greenlife@gmail.com", "1144445555");
  }
  private static String capitalized(String name) {
    return Strings.isNullOrEmpty(name) ? name : name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }
}