package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;
import org.example.model.recipe.Recipe;
import org.example.model.roles.Customer;
import org.example.service.CustomerHistoryService;
import org.example.service.CustomerService;
import org.example.service.DayService;
import org.example.service.RecipeService;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.example.Application.gson;

public class DayController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        RecipeService recipeService = new RecipeService(entityManager);
        CustomerService customerService = new CustomerService(entityManager);
        DayService dayService = new DayService(entityManager);
        CustomerHistoryService customerHistoryService = new CustomerHistoryService(entityManager);


        Spark.post("/meal",(req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();

            String weekDayName = LocalDate.now().getDayOfWeek().name();

            String mealType = gson.fromJson(jsonObject.get("mealType"), String.class);
            String recipeId = gson.fromJson(jsonObject.get("recipeId"), String.class);
            Recipe recipe = recipeService.getRecipeById(Long.parseLong(recipeId));
            String username = gson.fromJson(jsonObject.get("username"), String.class);
            Customer customer = customerService.getCustomerByName(username);


            CustomerHistory customerHistory = customer.getCustomerHistory();
            List<Day> days = customerHistory.getDays();

            if(!days.isEmpty()){
                Day lastDay = days.get(days.size()-1);

                if(Objects.equals(lastDay.getDayName(), weekDayName)){
                    dayService.updateDay(lastDay.getDayId(), recipe, mealType);
                }
                else{
                    //Dudoso el uso de DayOfWeek.valueOf(weekDayName)
                    //Day createdDay = dayService.createDay(DayOfWeek.valueOf(weekDayName), customerHistory);
                    customerHistory = customerHistoryService.updateCustomerHistory(customerHistory.getCustomerHistoryId(), DayOfWeek.valueOf(weekDayName));
                    Day createdDay = customerHistory.getDays().get(customerHistory.getDays().size()-1);
                    dayService.updateDay(createdDay.getDayId(), recipe, mealType);
                }
            }else{
                //Dudoso el uso de DayOfWeek.valueOf(weekDayName)
                //Day createdDay = dayService.createDay(DayOfWeek.valueOf(weekDayName), customerHistory);
                customerHistory = customerHistoryService.updateCustomerHistory(customerHistory.getCustomerHistoryId(), DayOfWeek.valueOf(weekDayName));
                Day createdDay = customerHistory.getDays().get(customerHistory.getDays().size()-1);
                dayService.updateDay(createdDay.getDayId(), recipe, mealType);
            }

            return gson.toJson("Meal added to Day "+ weekDayName + " successfully");
        });

        Spark.get("/getDays/:username", (req, res) -> {
            String username = req.params(":username");
            Customer customer = customerService.getCustomerByName(username);
            if (customer == null) {
                res.status(404); // 404 Not Found status code
                return "Customer not found";
            }
            CustomerHistory customerHistory = customer.getCustomerHistory();
            List<Day> days = customerHistory.getDays();
            List<Day> lastSeven = getLastSeven(days);
            System.out.println(lastSeven);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String result = gson.toJson(lastSeven);
            System.out.println(result);
            return result;
        }, gson::toJson);
    }

    private List<Day> getLastSeven(List<Day> days) {
        if (days.size() <= 7) {
            return days;
        } else {
            return days.subList(days.size() - 7, days.size());
        }
    }

}
