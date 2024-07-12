package org.example.controller;

import com.google.gson.*;
import okhttp3.*;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;
import org.example.service.CustomerMessageService;
import org.example.service.IngredientService;
import org.example.service.StockService;
import org.example.service.StoreService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.example.Application.gson;

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
            String storeName = jsonObject.get("storeName").getAsString();
            Long ingredientId = jsonObject.get("ingredientId").getAsLong();
            int quantity = jsonObject.get("quantity").getAsInt();
            String brand = jsonObject.get("brand").getAsString();
            double price = gson.fromJson(jsonObject.get("price"), Double.class);
            boolean stockExists = stockService.checkStockExists(storeName, ingredientId, brand);
            if (!stockExists) {
                stockService.createStock(storeName, ingredientId, quantity, brand, price);
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
            double price = gson.fromJson(jsonObject.get("price"), Double.class);
            stockService.updateStock(stockId, quantity, price);
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
                stockService.addToCart(storeName, ingredientName, quantity);

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
            stockService.purchaseCart();
            res.redirect("http://localhost:3000/dashboardCustomer");
            return null; // Return null since redirect has been issued
        });

        Spark.get("/failure", (req, res) -> {
            String paymentId = req.queryParams("payment_id");
            String status = req.queryParams("status");
            stockService.emptyCart();
            return "Payment ID: " + paymentId + " Status: " + status;
        });
    }
}