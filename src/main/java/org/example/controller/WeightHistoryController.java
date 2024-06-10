package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.history.Day;
import org.example.model.history.WeightHistory;
import org.example.model.roles.Customer;
import org.example.service.CustomerService;
import org.example.service.WeightHistoryService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Application.gson;

public class WeightHistoryController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        WeightHistoryService weightHistoryService = new WeightHistoryService(entityManagerFactory.createEntityManager());
        CustomerService customerService = new CustomerService(entityManagerFactory.createEntityManager());

        Spark.post("/addWeight", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String weight = gson.fromJson(jsonObject.get("weight"), String.class);
            String customerName = gson.fromJson(jsonObject.get("customer"), String.class);
            try {
                weightHistoryService.addWeight(customerName, Double.parseDouble(weight));
                res.status(200);
                return "Weight added successfully!";
            }
            catch(Error error)  {
                res.status(400);
                return "Failed adding weight!";
            }
        });


        Spark.get("/getWeight/:username/:date", (req, res) -> {
           String username = req.params(":username");
            Customer customer = customerService.getCustomerByName(username);
            String date = req.params(":date");
            // convert the date string to a LocalDate object
            LocalDate fromDate = LocalDate.parse(date);

            if (customer == null) {
                res.status(404); // 404 Not Found status code
                return "Customer not found";
            }

            List<WeightHistory> weightHistoryList = customer.getWeightHistory();

            List<WeightHistory> requestedEntries = getByDate(weightHistoryList, fromDate);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            return gson.toJson(requestedEntries);
        }, gson::toJson);
    }

    private List<WeightHistory> getByDate(List<WeightHistory> weightHistoryList, LocalDate fromDate) {
        return weightHistoryList.stream()
                .filter(weightHistory -> !weightHistory.getDate().isBefore(fromDate))
                .collect(Collectors.toList());
    }

}
