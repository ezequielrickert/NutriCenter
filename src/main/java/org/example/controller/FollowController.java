package org.example.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Store;
import org.example.service.CustomerService;
import org.example.service.IngredientService;
import org.example.service.StoreService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.example.Application.gson;

public class FollowController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        CustomerService customerService = new CustomerService(entityManagerFactory.createEntityManager());
        StoreService storeService = new StoreService(entityManagerFactory.createEntityManager());
        IngredientService ingredientService = new IngredientService(entityManagerFactory.createEntityManager());

        Spark.put("/follow/store", (req, resp) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String storeName = gson.fromJson(jsonObject.get("store"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            Store store = storeService.getStoreByUsername(storeName);
            Customer customer = customerService.getCustomerByName(customerName);
            try {
                customerService.followStore(store, customer);
                resp.status(200);
                return "Subscribed Successfully!";
            }
            catch(Error error)  {
                resp.status(400);
                return "Failed Subscribing!";
            }
        });

        Spark.delete("/follow/store", (req, resp) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String storeName = gson.fromJson(jsonObject.get("store"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            Store store = storeService.getStoreByUsername(storeName);
            Customer customer = customerService.getCustomerByName(customerName);
            try {
                customerService.unfollowStore(store, customer);
                resp.status(200);
                return "Unsubscribed Successfully!";
            }
            catch(Error error)  {
                resp.status(400);
                return "Failed Unsubscribing!";
            }
        });

        Spark.get("/follow/store", (req, resp) -> {
            String storeName = req.queryParams("store");
            String customerName = req.queryParams("customer");
            Store store = storeService.getStoreByUsername(storeName);
            Customer customer = customerService.getCustomerByName(customerName);
            resp.status(200);
            if (customerService.followsStore(store, customer)) {
                return true;
            }
            else {
                return false;
            }
        });

        Spark.put("/follow/ingredient", (req, resp) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String ingredientName = gson.fromJson(jsonObject.get("ingredient"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            Customer customer = customerService.getCustomerByName(customerName);
            Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
            try {
                customerService.followIngredient(ingredient, customer);
                resp.status(200);
                return "Subscribed Successfully!";
            }
            catch(Error error)  {
                resp.status(400);
                return "Failed Subscribing!";
            }
        });

        Spark.delete("/follow/ingredient", (req, resp) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String ingredientName = gson.fromJson(jsonObject.get("ingredient"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            Customer customer = customerService.getCustomerByName(customerName);
            Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
            try {
                customerService.unfollowIngredient(ingredient, customer);
                resp.status(200);
                return "Unsubscribed Successfully!";
            }
            catch(Error error)  {
                resp.status(400);
                return "Failed Unsubscribing!";
            }
        });

    Spark.get("/follow/ingredient", (req, resp) -> {
        String ingredientName = req.queryParams("ingredient");
        String customerName = req.queryParams("customer");
        Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
        Customer customer = customerService.getCustomerByName(customerName);
        resp.status(200);
        if (customerService.followsIngredient(ingredient, customer)) {
            return true;
        }
        else {
            return false;
        }
    });
    }
}
