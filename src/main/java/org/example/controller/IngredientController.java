package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.recipe.Allergy;
import org.example.model.recipe.Ingredient;
import org.example.service.IngredientService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.example.Application.gson;

public class IngredientController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        IngredientService ingredientService = new IngredientService(entityManagerFactory.createEntityManager());

        Spark.post("/createIngredient", (req, res) -> {
            String body = req.body();
            Ingredient ingredient = gson.fromJson(body, Ingredient.class);
            String ingredientName = ingredient.getIngredientName();
            Allergy allergy = ingredient.getAllergy();
            int proteins = ingredient.getProteins();
            int sodium = ingredient.getSodium();
            int calories = ingredient.getCalories();
            int totalFat = ingredient.getTotalFat();
            int cholesterol = ingredient.getCholesterol();
            int totalCarbohydrate = ingredient.getTotalCarbohydrate();
            ingredientService.createIngredient(ingredientName, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
            return ingredient;
        }, gson::toJson);

        Spark.post("/updateIngredient", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Ingredient ingredient = gson.fromJson(jsonObject.get("ingredient"), Ingredient.class);
            String name = gson.fromJson(jsonObject.get("ingredientName"), String.class);
            Allergy allergy = gson.fromJson(jsonObject.get("allergy"), Allergy.class);
            int proteins = gson.fromJson(jsonObject.get("proteins"), Integer.class);
            int sodium = gson.fromJson(jsonObject.get("sodium"), Integer.class);
            int calories = gson.fromJson(jsonObject.get("calories"), Integer.class);
            int totalFat = gson.fromJson(jsonObject.get("totalFat"), Integer.class);
            int cholesterol = gson.fromJson(jsonObject.get("cholesterol"), Integer.class);
            int totalCarbohydrate = gson.fromJson(jsonObject.get("totalCarbohydrate"), Integer.class);
            ingredientService.updateIngredient(ingredient.getIngredientId(), name,
                    allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
            return ingredient.asJson();
        }, gson::toJson);

        Spark.post("/deleteIngredient", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Ingredient ingredient = gson.fromJson(jsonObject.get("ingredient"), Ingredient.class);
            Long id = ingredient.getIngredientId();
            ingredientService.deleteIngredient(id);
            return ingredient;
        }, gson::toJson);

        Spark.get("/ingredients", (req, res) -> {
            List<Ingredient> ingredients = ingredientService.getAll();
            System.out.println(ingredients);
            return gson.toJson(ingredients);
        }, gson::toJson);

        Spark.get("/ingredients/search/:term", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                String searchTerm = request.params(":term");
                List<Ingredient> ingredients = ingredientService.searchIngredientsByName(searchTerm);
                return gson.toJson(ingredients);
            }
        });

        Spark.get("/ingredients/:ingredientName", (req, res) -> {
            String ingredientName = req.params(":ingredientName");
            Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(ingredient);
        });

        Spark.get("/ingredients/begins/:beginning", (req, res) -> {
            String beginning = req.params(":beginning");
            List<Ingredient> ingredients = ingredientService.getIngredientsBeginningWith(beginning);
            return gson.toJson(ingredients);
        });
    }
}