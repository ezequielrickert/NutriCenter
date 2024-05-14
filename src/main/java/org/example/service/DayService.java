package org.example.service;

import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;
import org.example.model.history.MealType;
import org.example.model.recipe.Recipe;
import org.example.repository.customerhistory.CustomerHistoryRepositoryImplementation;
import org.example.repository.day.DayRepository;
import org.example.repository.day.DayRepositoryImpl;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

public class DayService {

    private EntityManager entityManager;
    private DayRepository dayRepository;

    public DayService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.dayRepository = new DayRepositoryImpl(entityManager);
    }

    public void createDay(DayOfWeek name, CustomerHistory customerHistory) {
        Day day = dayRepository.createDay(name, customerHistory);
        CustomerHistoryRepositoryImplementation repositoryImplementation = new CustomerHistoryRepositoryImplementation(entityManager);
        repositoryImplementation.updateCustomerHistory(customerHistory.getCustomerHistoryId(), day);
    }

    public void readDay(Long id) {
        dayRepository.readDay(id);
    }

    public void updateDay(Long id, Recipe recipe, String mealType) {
        MealType traslatedMealType = translateMealType(mealType);
        dayRepository.updateDay(id, recipe, traslatedMealType);
    }

    public void deleteDay(Long id) {
        dayRepository.deleteDay(id);
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
