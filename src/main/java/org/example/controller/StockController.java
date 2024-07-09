package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.service.CustomerMessageService;
import org.example.service.StockService;
import org.example.service.StoreService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.example.Application.gson;

public class StockController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        StockService stockService = new StockService(entityManagerFactory.createEntityManager());
        StoreService storeService = new StoreService(entityManagerFactory.createEntityManager());
        CustomerMessageService customerMessageService = new CustomerMessageService(entityManagerFactory.createEntityManager());

        Spark.post("/addStock", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String storeName = gson.fromJson(jsonObject.get("storeName"), String.class);
            Ingredient ingredient = gson.fromJson(jsonObject.get("ingredientId"), Ingredient.class);
            int quantity = gson.fromJson(jsonObject.get("quantity"), Integer.class);
            String brand = gson.fromJson(jsonObject.get("brand"), String.class);
            double price = gson.fromJson(jsonObject.get("price"), Double.class);
            stockService.createStock(storeName, ingredient, quantity, brand, price);
            return gson.toJson("Stock created successfully");
        });

        Spark.get("/stock/:storeName", (req, res) -> {
            String storeName = req.params(":storeName");
            List<Stock> stock = stockService.readStock(storeName);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(stock);
        });

        Spark.post("/updateStock", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String store = gson.fromJson(jsonObject.get("storeName"), String.class);
            Ingredient ingredient = gson.fromJson(jsonObject.get("ingredientId"), Ingredient.class);
            int quantity = gson.fromJson(jsonObject.get("quantity"), Integer.class);
            String brand = gson.fromJson(jsonObject.get("brand"), String.class);
            double price = gson.fromJson(jsonObject.get("price"), Double.class);
            stockService.updateStock(store, ingredient, quantity, brand, price);
            return gson.toJson("Stock updated successfully");
        });

        Spark.post("/deleteStock", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String store = gson.fromJson(jsonObject.get("storeName"), String.class);
            Ingredient ingredient = gson.fromJson(jsonObject.get("ingredientId"), Ingredient.class);
            stockService.deleteStock(store, ingredient.getIngredientId());
            return gson.toJson("Stock deleted successfully");
        });

        Spark.get("/sellingStores/:ingredientName", (req, res) -> {
            String ingredientName = req.params(":ingredientName");
            List<Store> stores = stockService.getStoresByIngredient(ingredientName);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(stores);
        });

        Spark.post("/message/:storeName/:message", (req, res) -> {
            String storeName = req.params(":storeName");
            String message = req.params(":message");
            Store store = storeService.getStoreByUsername(storeName);
            List<Customer> customers = store.getCustomers();
            customerMessageService.createMessage(message, customers);
            return gson.toJson("Message sent successfully");
        });

        Spark.post("/purchase", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String storeName = gson.fromJson(jsonObject.get("storeName"), String.class);
            return gson.toJson("Purchase successful");
        });
    }
}