package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.service.StockService;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.example.Application.gson;

public class StockController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        StockService stockService = new StockService(entityManagerFactory.createEntityManager());

        Spark.post("/addStock", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String storeName = gson.fromJson(jsonObject.get("storeName"), String.class);
            Ingredient ingredient = gson.fromJson(jsonObject.get("ingredientId"), Ingredient.class);
            int quantity = gson.fromJson(jsonObject.get("quantity"), Integer.class);
            String brand = gson.fromJson(jsonObject.get("brand"), String.class);
            stockService.createStock(storeName, ingredient, quantity, brand);
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
            stockService.updateStock(store, ingredient, quantity, brand);
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
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<Store> stores = stockService.getStoresByIngredient(ingredientName);
            return gson.toJson(stores);
        });

    }

}