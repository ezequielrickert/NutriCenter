package org.example.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.roles.Customer;
import org.example.service.CustomerService;
import org.example.service.SignUpService;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.example.Application.gson;

public class CustomerController {

    public void run() {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
    SignUpService signUpService = new SignUpService(entityManagerFactory.createEntityManager());
    CustomerService customerService = new CustomerService(entityManagerFactory.createEntityManager());

    Spark.post("/createCustomer", (req, res) -> {
      String body = req.body();
      JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
      String name =  gson.fromJson(jsonObject.get("customerName"), String.class);
      String email = gson.fromJson(jsonObject.get("customerEmail"), String.class);
      String password = gson.fromJson(jsonObject.get("customerPassword"), String.class);
      if (!signUpService.validate(name)) {
        res.status(401);
        return "Username already exists";
      }
      customerService.createUser(name, email, password);
        res.status(201); // 201 Created status code
        return "Customer created successfully";
    }, gson::toJson);

    Spark.get("/persisted-customers/:id", (req, resp) -> {
        final String id = req.params("id");
        /* Business Logic */
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction tx = entityManager.getTransaction();
        Customer customer = customerService.readUser(Long.parseLong(id));
        resp.type("application/json");
        return customer.asJson();
      }
    );
  }
}
