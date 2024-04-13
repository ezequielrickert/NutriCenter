package org.example;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.example.controller.*;
import org.example.model.*;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Console;
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

    createSuperAdmin(entityManagerFactory.createEntityManager());

      Spark.options("/*", (request, response) -> {

          String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
          if (accessControlRequestHeaders != null) {
              response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
          }

          String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
          if (accessControlRequestMethod != null) {
              response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
          }

          return "OK";
      });

      Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

      // Post to create Customer
      Spark.post("/createCustomer", (req, res) -> {
          String body = req.body();
          Customer newCustomer = gson.fromJson(body, Customer.class);
          String username = newCustomer.getCustomerName();
          String email = newCustomer.getCustomerEmail();
          String password = newCustomer.getCustomerPassword();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          CustomerController customerController = new CustomerController(entityManager);
          customerController.createClient(username, email, password);
          return newCustomer;
      }, gson::toJson);


      // Post to fetch Customer (probably wrong...)
      Spark.post("/fetchCustomer", (req, res) -> {
          String body = req.body();
          Customer newCustomer = gson.fromJson(body, Customer.class);
          String username = newCustomer.getCustomerName();
          String password = newCustomer.getCustomerPassword();
          return req;
      }, gson::toJson);

      // Post to create SuperAdmin
      Spark.post("/createSuperAdmin", (req, res) -> {
         String body = req.body();
         SuperAdmin superAdmin = gson.fromJson(body, SuperAdmin.class);
         String username = superAdmin.getAdminUsername();
         String password = superAdmin.getAdminPassword();
         EntityManager entityManager = entityManagerFactory.createEntityManager();
         SuperAdminController superAdminController = new SuperAdminController(entityManager);
         superAdminController.createSuperAdmin(username, password);
         return superAdmin;
      } , gson::toJson);

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

      // Post to create Nutritionist
      Spark.post("/createNutritionist", (req, res) -> {
          String body = req.body();
          Nutritionist nutritionist = gson.fromJson(body, Nutritionist.class);
          String username = nutritionist.getNutritionistName();
          String email = nutritionist.getNutritionistEmail();
          String diploma = nutritionist.getEducationDiploma();
          String password = nutritionist.getNutritionistPassword();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          NutritionistController nutritionistController = new NutritionistController(entityManager);
          nutritionistController.createNutritionist(username, email, password, diploma);
          return nutritionist;
      } , gson::toJson);

      // Post to create Ingredient
      Spark.post("/createIngredient", (req, res) -> {
          String body = req.body();
          Ingredient ingredient = gson.fromJson(body, Ingredient.class);
          String name = ingredient.getIngredientName();
          Allergy allergy = ingredient.getAllergy();
          int protein = ingredient.getProtein();
          int sodium = ingredient.getSodium();
          int calories = ingredient.getCalories();
          int totalFat = ingredient.getTotalFat();
          int cholesterol = ingredient.getCholesterol();
          int totalCarbohydrate = ingredient.getTotalCarbohydrate();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          IngredientController ingredientController = new IngredientController(entityManager);
          ingredientController.createIngredient(name, allergy, protein, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
          return ingredient;
      } , gson::toJson);

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
    customerController.createClient("erickert", "erickert@mail.austral.edu.ar", "lal");
    customerController.createClient("hlagos", "hlagos@mail.austral.edu.ar", "lal");
    customerController.createClient("tbernardez", "tbernardez@mail.austral.edu.ar", "lal");
  }

  private static void storedNutritionist(EntityManager entityManager){
    NutritionistController nutritionistController = new NutritionistController(entityManager);
    nutritionistController.createNutritionist("yael", "yael@test.com", "sas","UA 4 year diploma");
    nutritionistController.createNutritionist("paola", "paola@test.com", "sas", "UBA 4 year diploma");
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

  private static void createSuperAdmin(EntityManager entityManager) {
      SuperAdminController superAdminController = new SuperAdminController(entityManager);
      superAdminController.createSuperAdmin("God", "God");
  }
}