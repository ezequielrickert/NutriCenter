package org.example.controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.roles.Nutritionist;
import org.example.model.stock.Stock;
import org.example.service.SignUpService;
import org.example.service.StockService;
import org.example.service.StoreService;
import org.example.model.roles.Store;
import org.example.repository.store.StoreRepositoryImpl;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

import static org.example.Application.gson;

public class StoreController {

  public void run() {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
    StoreService storeService = new StoreService(entityManagerFactory.createEntityManager());
    SignUpService signUpService = new SignUpService(entityManagerFactory.createEntityManager());
    StockService stockService = new StockService(entityManagerFactory.createEntityManager());

    Spark.get("/store/:id", (req, resp) -> {
      final String id = req.params(":id");
      Store store = storeService.getStoreByUsername(id);
      Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      resp.type("application/json");
      return specialGson.toJson(store);
    });

    Spark.post("/store", (req, res) -> {
      String body = req.body();
      JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
      String name =  gson.fromJson(jsonObject.get("storeName"), String.class);
      if (!signUpService.validate(name)) {
        res.status(401);
        return "Username already exists";
      }
      String email = gson.fromJson(jsonObject.get("storeMail"), String.class);
      String password = gson.fromJson(jsonObject.get("storePassword"), String.class);
      storeService.createStore(name, email, password);
      res.status(201);
      return "Store created successfully";
    }, gson::toJson);

    //Saque update y delete porque no se usan

    Spark.get("/storeFill/:searchTerm", (req, resp) -> {
      final String searchTerm = req.params(":searchTerm");
      List<Store> stores = storeService.getStoresBySearchTerm(searchTerm);
      Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      resp.type("application/json");
      return specialGson.toJson(stores);
    });
  }
}