package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;
import org.example.service.CustomerService;
import org.example.service.NutritionistService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.example.Application.gson;

public class SubscriptionController {
    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        CustomerService customerService = new CustomerService(entityManagerFactory.createEntityManager());
        NutritionistService nutritionistService = new NutritionistService(entityManagerFactory.createEntityManager());

        Spark.post("/subscribe", (req, resp) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String nutritionistName = gson.fromJson(jsonObject.get("nutritionist"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            Customer customer = customerService.getCustomerByName(customerName);
            Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(nutritionistName);
            try {
                customerService.subscribe(nutritionist, customer);
                nutritionistService.subscribe(nutritionist, customer);
                resp.status(200);
                return "Subscribed Successfully!";
            }
            catch(Error error)  {
                resp.status(400);
                return "Failed Subscribing!";
            }
        });

        Spark.delete("/subscribe" , (req, resp) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String nutritionistName = gson.fromJson(jsonObject.get("nutritionist"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            Customer customer = customerService.getCustomerByName(customerName);
            Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(nutritionistName);
            try {
                customerService.unsubscribe(nutritionist.getNutritionistName(), customer);
                nutritionistService.unsubscribe(nutritionist.getNutritionistName(), customer);
                resp.status(200);
                return "Unsubscribed Successfully!";
            }
            catch(Error error)  {
                resp.status(400);
                return "Failed Unsubscribing!";
            }
        });

        Spark.get("/subscription/nutritionist/:username", (req, resp) -> {
           String username = req.params(":username");
           Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(username);
           List<Customer> customers = nutritionist.getCustomers();
           List<String> customerUsernames = customers.stream().map(Customer::getCustomerName).toList();
            Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            resp.type("application/json");
            return specialGson.toJson(customerUsernames);
        });

        Spark.get("/subscription/customer/:username", (req, resp) -> {
            String username = req.params(":username");
            Customer customer = customerService.getCustomerByName(username);
            List<Nutritionist> nutritionists = customer.getNutritionists();
            List<String> nutritionistUsernames = nutritionists.stream().map(Nutritionist::getNutritionistName).toList();
            Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            resp.type("application/json");
            return specialGson.toJson(nutritionistUsernames);
        });

        Spark.get("/subscribe", (req, resp) -> {
            String nutritionistName = req.queryParams("nutritionist");
            String customerName = req.queryParams("customer");
            Customer customer = customerService.getCustomerByName(customerName);
            Boolean isSubscribed = customerService.isSubscribed(nutritionistName, customer);
            resp.status(200);
            return gson.toJson(isSubscribed);
        });
    }
}
