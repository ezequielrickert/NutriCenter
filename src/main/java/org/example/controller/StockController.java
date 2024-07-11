package org.example.controller;

import com.google.gson.*;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import okhttp3.*;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.service.CustomerMessageService;
import org.example.service.IngredientService;
import org.example.service.StockService;
import org.example.service.StoreService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.example.Application.gson;

import com.mercadopago.MercadoPagoConfig;

public class StockController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        StockService stockService = new StockService(entityManagerFactory.createEntityManager());
        StoreService storeService = new StoreService(entityManagerFactory.createEntityManager());
        IngredientService ingredientService = new IngredientService(entityManagerFactory.createEntityManager());
        CustomerMessageService customerMessageService = new CustomerMessageService(entityManagerFactory.createEntityManager());
        final OkHttpClient client = new OkHttpClient();

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
            JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
            JsonArray itemsArray = new JsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String storeName = jsonObject.get("store").getAsString();
                String ingredientName = jsonObject.get("ingredient").getAsString();
                int quantity = jsonObject.get("quantity").getAsInt();
                BigDecimal price = jsonObject.get("price").getAsBigDecimal();

                // Supone que las clases Ingredient y stockService están definidas en otro lugar
                Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
                stockService.purchase(storeName, ingredientName, quantity);

                JsonObject itemRequest = new JsonObject();
                itemRequest.addProperty("id", ingredient.getIngredientId().toString());
                itemRequest.addProperty("title", ingredient.getIngredientName());
                itemRequest.addProperty("quantity", quantity);
                itemRequest.addProperty("unit_price", price);

                itemsArray.add(itemRequest);
            }

            JsonObject preferenceRequest = new JsonObject();
            JsonObject backUrls = new JsonObject();
            backUrls.addProperty("success", "http://localhost:8080/success");
            backUrls.addProperty("failure", "http://localhost:8080/failure");
            preferenceRequest.addProperty("auto_return", "all");
            preferenceRequest.add("items", itemsArray);
            preferenceRequest.add("back_urls", backUrls);

            // Enviar la solicitud POST a Mercado Pago
            RequestBody requestBody = RequestBody.create(preferenceRequest.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url("https://api.mercadopago.com/checkout/preferences")
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + "TEST-8978123681705863-071114-8f7f0750deb105168657eed24d990cbd-792894021")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseJson = response.body().string();
                    JsonObject responseObj = JsonParser.parseString(responseJson).getAsJsonObject();
                    String initPoint = responseObj.get("init_point").getAsString();

                    // Retorna el init_point en la respuesta
                    JsonObject responseJsonObj = new JsonObject();
                    responseJsonObj.addProperty("init_point", initPoint);
                    res.type("application/json");
                    return responseJsonObj.toString();
                } else {
                    res.status(response.code());
                    return "Error: Unable to create preference";
                }
            } catch (IOException e) {
                res.status(500);
                return "Error: " + e.getMessage();
            }
        });

        Spark.get("/success", (req, res) -> {
            String paymentId = req.queryParams("payment_id");
            String status = req.queryParams("status");
//            stockService.apply();
            return "Payment ID: " + paymentId + " Status: " + status;
        });

        Spark.get("/failure", (req, res) -> {
            String paymentId = req.queryParams("payment_id");
            String status = req.queryParams("status");
//            stockService.clear();
            return "Payment ID: " + paymentId + " Status: " + status;
        });
    }
}