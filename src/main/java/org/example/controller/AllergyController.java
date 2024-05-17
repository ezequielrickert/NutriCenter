package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.service.AllergyService;
import org.example.model.recipe.Allergy;
import spark.Spark;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.example.Application.gson;

public class AllergyController{

    public void run(){

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

        Spark.get("/allergies", (req, res) -> {
            AllergyService allergyService = new AllergyService(entityManagerFactory.createEntityManager());
            List<Allergy> allergies = allergyService.getAllergiesOrderedByName();
            System.out.println(allergies);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String result = gson.toJson(allergies);
            System.out.println(result);
            return result;
        }, gson::toJson);
    }

}
