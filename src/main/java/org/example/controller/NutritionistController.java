package org.example.controller;
import org.example.model.roles.Nutritionist;
import org.example.repository.nutritionist.NutritionistRepositoryImp;

import javax.persistence.EntityManager;
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

    Spark.get("/nutritionist/:username", (req, resp) -> {
      final String username = req.params("username");
      Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(username);
      resp.type("application/json");
      return nutritionist.asJson();
    });

    Spark.post("/createNutritionist", (req, res) -> {
      String body = req.body();
      Nutritionist nutritionist = gson.fromJson(body, Nutritionist.class);
      String name = nutritionist.getNutritionistName();
      if (!signUpService.validate(name)) {
        res.status(401);
        return "Username already exists";
      }
      String mail = nutritionist.getNutritionistEmail();
      String diploma = nutritionist.getEducationDiploma();
      String password = nutritionist.getNutritionistPassword();
      nutritionistService.createNutritionist(name, mail, password, diploma);
      return nutritionist;
    }, gson::toJson);

    Spark.get("/nutritionistFill/:searchTerm", (req, resp) -> {
      final String username = req.params("searchTerm");
      List<Nutritionist> nutritionist = nutritionistService.nutririonistWildcard(username);
      resp.type("application/json");
      return gson.toJson(nutritionist);
    });
  }

}
