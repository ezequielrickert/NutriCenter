package org.example;

import com.google.gson.*;
import org.example.controller.*;
import spark.Spark;

import java.util.TimeZone;

public class Application {

    public static final Gson gson = new Gson();

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));

        Spark.port(8080);

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

        new AllergyController().run();
        new CategoryController().run();
        new CustomerController().run();
        new IngredientController().run();
        new LoginController().run();
        new NutritionistController().run();
        new RecipeController().run();
        new StockController().run();
        new StoreController().run();
        new SuperAdminController().run();
        new DayController().run();
        new SubscriptionController().run();
        new FollowController().run();
        new WeightHistoryController().run();
        new CustomerMessageController().run();
    }
}