package org.example.controller;

import org.example.model.history.CustomerHistory;
import org.example.model.recipe.Recipe;
import org.example.service.DayService;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

public class DayController {

    EntityManager entityManager;
    DayService dayService;

    public DayController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.dayService = new DayService(entityManager);
    }

    public void createDay(DayOfWeek name, CustomerHistory customerHistory) {
        dayService.createDay(name, customerHistory);
    }

    public void readDay(Long id) {
        dayService.readDay(id);
    }

    public void updateDay(Long id, Recipe recipe, String mealType) {
        dayService.updateDay(id, recipe, mealType);
    }

    public void deleteDay(Long id) {
        dayService.deleteDay(id);
    }

}
