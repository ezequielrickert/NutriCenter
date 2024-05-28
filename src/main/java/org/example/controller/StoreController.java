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
      resp.type("application/json");
      return store.asJson();
    });

    Spark.post("/createStore", (req, res) -> {
      String body = req.body();
      Store store = gson.fromJson(body, Store.class);
      String storeName = store.getStoreName();
      if (!signUpService.validate(storeName)) {
        res.status(401);
        return "Username already exists";
      }
      String storeMail = store.getStoreMail();
      String storePassword = store.getStorePassword();
      storeService.createStore(storeName, storeMail, storePassword);
      return store;
    }, gson::toJson);

    Spark.post("/updateStore", (req, res) -> {
      String body = req.body();
      JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
      Store store = gson.fromJson(jsonObject.get("store"), Store.class);
      String storeName = gson.fromJson(jsonObject.get("storeName"), String.class);
      String storeMail = gson.fromJson(jsonObject.get("storeEmail"), String.class);
      String storePassword = gson.fromJson(jsonObject.get("storePassword"), String.class);
      storeService.updateStore(store.getStoreId(), storeName, storeMail, storePassword);
      return store.asJson();
    }, gson::toJson);

    Spark.post("/deleteStore", (req, res) -> {
      String body = req.body();
      JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
      Store store = gson.fromJson(jsonObject.get("store"), Store.class);
      Long id = store.getStoreId();
      storeService.deleteStore(id);
      return store;
    }, gson::toJson);

    Spark.get("/persisted-store/:id", (req, resp) -> {
      final String id = req.params("id");
        Store store = storeService.readStore(Long.parseLong(id));
      resp.type("application/json");
      return store.asJson();
    });

    Spark.get("/storeFill/:searchTerm", (req, resp) -> {
        final String searchTerm = req.params(":searchTerm");
        List<Store> stores = storeService.getStoresBySearchTerm(searchTerm);
        resp.type("application/json");
        return gson.toJson(stores);
    });

  }

}