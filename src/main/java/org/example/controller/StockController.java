package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;
import org.example.service.CustomerMessageService;
import org.example.service.CustomerService;
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
            String storeName = jsonObject.get("storeName").getAsString();
            Long ingredientId = jsonObject.get("ingredientId").getAsLong();
            int quantity = jsonObject.get("quantity").getAsInt();
            String brand = jsonObject.get("brand").getAsString();

            boolean stockExists = stockService.checkStockExists(storeName, ingredientId, brand);
            if (!stockExists) {
                stockService.createStock(storeName, ingredientId, quantity, brand);
                return gson.toJson("Stock created successfully");
            } else {
                return gson.toJson("Stock with the given ingredient and brand already exists in this store");
            }
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
            StockId stockId = gson.fromJson(jsonObject.get("stockId"), StockId.class);
            String storeName = jsonObject.get("storeName").getAsString();
            int quantity = jsonObject.get("quantity").getAsInt();

            stockService.updateStock(stockId, quantity);
            return gson.toJson("Stock updated successfully");
        });

        Spark.post("/deleteStock", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            StockId stockId = gson.fromJson(jsonObject.get("stockId"), StockId.class);

            stockService.deleteStock(stockId);
            return new Gson().toJson("Stock deleted successfully");
        });

        Spark.get("/sellingStores/:ingredientName", (req, res) -> {
            String ingredientName = req.params(":ingredientName");
            List<Store> stores = stockService.getStoresByIngredient(ingredientName);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(stores);
        });

        Spark.post("/message/:storeName/:ingredientName/:quantity", (req, res) -> {
            String storeName = req.params(":storeName");
            String ingredientName = req.params(":ingredientName");
            Integer quantity = Integer.parseInt(req.params(":quantity"));
            Store store = storeService.getStoreByUsername(storeName);
            List<Customer> customers = store.getCustomers();
            customerMessageService.createMessage(customers, storeName, ingredientName, quantity);
            return gson.toJson("Message sent successfully");
        });

        Spark.post("/message/:storeName/:ingredientName", (req, res) -> {
            String storeName = req.params(":storeName");
            String ingredientName = req.params(":ingredientName");
            Store store = storeService.getStoreByUsername(storeName);
            List<Customer> customers = store.getCustomers();
            customerMessageService.createMessage(customers, storeName, ingredientName, -1);
            return gson.toJson("Message sent successfully");
        });
    }
}
