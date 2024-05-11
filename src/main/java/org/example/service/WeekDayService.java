package org.example.service;

import org.example.model.history.MealType;
import org.example.model.recipe.Recipe;
import org.example.repository.weekday.WeekDayRepository;
import org.example.repository.weekday.WeekDayRepositoryImpl;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

public class WeekDayService {

    private EntityManager entityManager;
    private WeekDayRepository weekDayRepository;

    public WeekDayService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.weekDayRepository = new WeekDayRepositoryImpl(entityManager);
    }

    public void createWeekDay(DayOfWeek name) {
        weekDayRepository.createWeekDay(name);
    }

    public void readWeekDay(Long id) {
        weekDayRepository.readWeekDay(id);
    }

    public void updateWeekDay(Long id, Recipe recipe, String mealType) {
        MealType traslatedMealType = translateMealType(mealType);
        weekDayRepository.updateWeekDay(id, recipe, traslatedMealType);
    }

    public void deleteWeekDay(Long id) {
        weekDayRepository.deleteWeekDay(id);
    }

    private MealType translateMealType(String mealType) {
        return switch (mealType) {
            case "Breakfast" -> MealType.BREAKFAST;
            case "Lunch" -> MealType.LUNCH;
            case "Dinner" -> MealType.DINNER;
            default -> throw new IllegalArgumentException("Invalid meal type");
        };
    }

}
