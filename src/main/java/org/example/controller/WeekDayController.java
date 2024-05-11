package org.example.controller;

import org.example.model.recipe.Recipe;
import org.example.service.WeekDayService;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

public class WeekDayController {

    EntityManager entityManager;
    WeekDayService weekDayService;

    public WeekDayController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.weekDayService = new WeekDayService(entityManager);
    }

    public void createWeekDay(DayOfWeek name) {
        weekDayService.createWeekDay(name);
    }

    public void readWeekDay(Long id) {
        weekDayService.readWeekDay(id);
    }

    public void updateWeekDay(Long id, Recipe recipe, String mealType) {
        weekDayService.updateWeekDay(id, recipe, mealType);
    }

    public void deleteWeekDay(Long id) {
        weekDayService.deleteWeekDay(id);
    }

}
