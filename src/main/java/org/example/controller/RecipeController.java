package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.example.model.recipe.Allergy;
import org.example.model.recipe.Category;
import org.example.model.recipe.Ingredient;
import org.example.model.recipe.Recipe;
import org.example.service.RecipeService;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.List;

import static org.example.Application.gson;

public class RecipeController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        RecipeService recipeService = new RecipeService(entityManagerFactory.createEntityManager());

        Spark.get("/recipes", (req, res) -> {
            List<Recipe> recipes = recipeService.getRecipesOrderedByName();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(recipes);
        }, gson::toJson);

        Spark.get("/recipes/searchId/:recipeId", (req, res) -> {
            String recipeId = req.params(":recipeId");
            Recipe recipe = recipeService.getRecipeById(Long.parseLong(recipeId));
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(recipe);
        });

        Spark.get("/publicRecipes/:recipe", (req, res) -> {
            String recipe = req.params(":recipe");
            List<Recipe> recipes = recipeService.getPublicRecipes(recipe);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(recipes);
        });


        Spark.get("/recipes/:username", (req, res) -> {
            String username = req.params(":username");
            List<Recipe> recipes = recipeService.getRecipeByUsername(username);
            System.out.println(recipes);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(recipes);
        });

        Spark.get("/publicRecipesByDietType", (req, res) -> {
           String searchTerm = req.queryParams("term");
           String dietType = req.queryParams("diet");
           List<Recipe> recipes = recipeService.getPublicRecipesByDietType(dietType, searchTerm);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(recipes);
        });

        Spark.post("/createRecipe", (req, res) -> {
            String body = req.body();
            Recipe recipe = gson.fromJson(body, Recipe.class);
            String recipeName = recipe.getRecipeName();
            String description = recipe.getRecipeDescription();
            List<Category> categorylist = recipe.getCategoryList();
            List<Ingredient> ingredientlist = recipe.getIngredientList();
            String username = recipe.getRecipeUsername();
            boolean isPublic = recipe.getIsPublic();
            recipeService.createRecipe(recipeName, description, categorylist, ingredientlist, username, isPublic);
            return recipe;
        }, gson::toJson);

        Spark.post("/updateRecipe", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Recipe recipe = gson.fromJson(jsonObject.get("recipe"), Recipe.class);
            String recipeName = gson.fromJson(jsonObject.get("recipeName"), String.class);
            String recipeDescription = gson.fromJson(jsonObject.get("recipeDescription"), String.class);

            Type categoryListType = new TypeToken<List<Category>>() {
            }.getType();
            List<Category> categoryList = gson.fromJson(jsonObject.get("categoryList"), categoryListType);

            Type ingredientListType = new TypeToken<List<Ingredient>>() {
            }.getType();
            List<Ingredient> ingredientList = gson.fromJson(jsonObject.get("ingredientList"), ingredientListType);

            Boolean isPublic = gson.fromJson(jsonObject.get("isPublic"), Boolean.class);

            recipeService.updateRecipe(recipe.getRecipeId(), recipeName, recipeDescription, categoryList,
                    ingredientList, isPublic);
            return recipe;
        }, gson::toJson);

        Spark.post("/deleteRecipe", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Recipe recipe = gson.fromJson(jsonObject.get("recipe"), Recipe.class);
            Long id = recipe.getRecipeId();
            recipeService.deleteRecipe(id);
            return recipe;
        }, gson::toJson);

    }

}
