package org.example;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.example.controller.CustomerController;
import org.example.model.Customer;
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

    storedCustomer(entityManagerFactory);

    /* 1. Basic Request */
    Spark.get("/hello",
            (req, resp) -> "Hello, World"
    );

    /* 2. Dynamic Content: Get Current Date & Time */
    Spark.get("/web/v1",
            (req, resp) -> {
              final String now = Instant.now().toString();
              return "<!DOCTYPE html>\n" +
                      "<html lang=\"en\">\n" +
                      "<head>\n" +
                      "  <meta charset=\"UTF-8\">\n" +
                      "  <title>Server side rendering v1</title>\n" +
                      "</head>\n" +
                      "<body>\n" +
                      "\n" +
                      "  <h1>Hora actual</h1>\n" +
                      "  <h3>" + now + "</h3>  \n" +
                      "\n" +
                      "</body>\n" +
                      "</html>";
            }
    );

    /* 3. Dynamic Content: Show User Details - Url params */
    Spark.get("/users/:name",
            (req, resp) -> {
              final String name = capitalized(req.params("name"));


              final Customer customer = new Customer(name , name + "@gmail.com");

              resp.type("application/json");

              return customer.asJson();
            }
    );

    /* 4. Dynamic Content: Show User Details - Query params */
    Spark.get("/users",
            (req, resp) -> {
              final String name = capitalized(req.queryParams("name"));
              final String lastname = capitalized(req.queryParams("lastname"));
              final String mail = req.queryParams("mail");
              final Customer customer = new Customer(name + lastname, mail + "@gmail.com");

              resp.type("application/json");

              return customer.asJson();
            }
    );

    /* 5. Dynamic Content from Db */
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


    /* 6. Receiving data from client */
//    Spark.post("/users", "application/json", (req, resp) -> {
//      final User user = User.fromJson(req.body());
//
//      /* Begin Business Logic */
//      final EntityManager entityManager = entityManagerFactory.createEntityManager();
//      final Users users = new Users(entityManager);
//      EntityTransaction tx = entityManager.getTransaction();
//      tx.begin();
//      users.persist(user);
//      resp.type("application/json");
//      resp.status(201);
//      tx.commit();
//      entityManager.close();
//      /* End Business Logic */
//
//      return user.asJson();
//    });

//    Spark.get("/users1", "application/json", (req, resp) -> {
//
//      resp.type("application/json");
//      resp.status(201);
//
//      User u1 = User.create("ja1").lastName("pee").build();
//      User u2 = User.create("ja2").lastName("pee").build();
//
//      List<User> users = Arrays.asList(u1, u2);
//      return gson.toJson(users);
//    });

//    Spark.options("/*", (req, res) -> {
//      String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
//      if (accessControlRequestHeaders != null) {
//        res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
//      }
//
//      String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
//      if (accessControlRequestMethod != null) {
//        res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
//      }
//
//      return "OK";
//    });

//    Spark.before((req, res) -> {
//      res.header("Access-Control-Allow-Origin", "*");
//      res.header("Access-Control-Allow-Headers", "*");
//      res.type("application/json");
//    });
  }

  private static void storedCustomer(EntityManagerFactory entityManagerFactory) {
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    CustomerController customerController = new CustomerController(entityManager);
    customerController.createClient("erickert", "erickert@mail.austral.edu.ar");
    customerController.createClient("hlagos", "hlagos@mail.austral.edu.ar");
    customerController.createClient("tbernardez", "tbernardez@mail.austral.edu.ar");
  }

  private static String capitalized(String name) {
    return Strings.isNullOrEmpty(name) ? name : name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }
}