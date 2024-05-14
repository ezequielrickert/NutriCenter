package org.example.controller;

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
      Customer newCustomer = gson.fromJson(body, Customer.class);
      String username = newCustomer.getCustomerName();
      if (!signUpService.validate(username)) {
        res.status(401);
        return "Username already exists";
      }
      String email = newCustomer.getCustomerEmail();
      String password = newCustomer.getCustomerPassword();
      customerService.createUser(username, email, password);
      return newCustomer;
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
