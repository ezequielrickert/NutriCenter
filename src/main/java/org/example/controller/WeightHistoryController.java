package org.example.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.service.WeightHistoryService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.example.Application.gson;

public class WeightHistoryController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        WeightHistoryService weightHistoryService = new WeightHistoryService(entityManagerFactory.createEntityManager());

        Spark.post("/addWeight", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String weight = gson.fromJson(jsonObject.get("weight"), String.class);
            String cutomerName = gson.fromJson(jsonObject.get("customer"), String.class);
            try {
                weightHistoryService.addWeight(cutomerName, Double.parseDouble(weight));
                res.status(200);
                return "Weight added successfully!";
            }
            catch(Error error)  {
                res.status(400);
                return "Failed adding weight!";
            }
        });
    }
}
