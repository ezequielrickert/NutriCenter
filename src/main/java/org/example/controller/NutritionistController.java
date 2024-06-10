package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.roles.Nutritionist;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.example.service.NutritionistService;
import org.example.service.SignUpService;
import spark.Spark;

import java.util.List;

import static org.example.Application.gson;

public class NutritionistController {

  public void run() {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
    NutritionistService nutritionistService = new NutritionistService(entityManagerFactory.createEntityManager());
    SignUpService signUpService = new SignUpService(entityManagerFactory.createEntityManager());

    Spark.get("/nutritionist/:id", (req, resp) -> {
      final String id = req.params(":id");
      Nutritionist nutritionist = nutritionistService.getNutritionist(Long.valueOf(id));
      Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      resp.type("application/json");
      return specialGson.toJson(nutritionist);
    });

    Spark.post("/createNutritionist", (req, res) -> {
      String body = req.body();
      JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
      String name =  gson.fromJson(jsonObject.get("nutritionistName"), String.class);
      if (!signUpService.validate(name)) {
        res.status(401);
        return "Username already exists";
      }
      String email = gson.fromJson(jsonObject.get("nutritionistEmail"), String.class);
      String password = gson.fromJson(jsonObject.get("nutritionistPassword"), String.class);
      String diploma = gson.fromJson(jsonObject.get("educationDiploma"), String.class);
      nutritionistService.createNutritionist(name, email, password, diploma);
        res.status(201);
        return "Nutritionist created successfully";
    }, gson::toJson);

    Spark.get("/nutritionistFill/:searchTerm", (req, resp) -> {
      final String username = req.params("searchTerm");
      List<Nutritionist> nutritionist = nutritionistService.nutritionistWildcard(username);
      Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      resp.type("application/json");
      return specialGson.toJson(nutritionist);
    });

    Spark.get("/nutritionist/name/:username", (req, resp) -> {
      final String username = req.params(":username");
      Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(username);
      Gson specialGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      resp.type("application/json");
      return specialGson.toJson(nutritionist);
    });
  }
}
