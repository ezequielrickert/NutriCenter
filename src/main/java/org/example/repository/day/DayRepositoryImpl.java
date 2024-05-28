package org.example.repository.day;

import org.example.model.history.CustomerHistory;
import org.example.model.history.MealType;
import org.example.model.history.Day;
import org.example.model.recipe.Recipe;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayRepositoryImpl implements DayRepository {

    EntityManager entityManager;

    public DayRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Day createDay(DayOfWeek dayName) {
        Day day = new Day(dayName, LocalDate.now());
        entityManager.persist(day);
        return day;
    }

    @Override
    public void readDay(Long weekDayId) {
        entityManager.getTransaction().begin();
        Day day = entityManager.find(Day.class, weekDayId);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateDay(Long weekDayId, Recipe recipe, MealType mealType) {
        entityManager.getTransaction().begin();
        Day day = entityManager.find(Day.class, weekDayId);
        updateMeal(recipe, mealType, day);
        entityManager.merge(day);
        entityManager.getTransaction().commit();
    }

    private static void updateMeal(Recipe recipe, MealType mealType, Day day) {
        switch (mealType) {
            case BREAKFAST:
                day.setBreakfast(recipe);
                break;
            case LUNCH:
                day.setLunch(recipe);
                break;
            case DINNER:
                day.setDinner(recipe);
                break;
            default:
                throw new IllegalArgumentException("Invalid meal type");
        }
    }

    @Override
    public void deleteDay(Long weekDayId) {
        entityManager.getTransaction().begin();

        Day day = entityManager.find(Day.class, weekDayId);
        if (day != null) {
            entityManager.remove(day);
        }
        entityManager.getTransaction().commit();
    }
}
