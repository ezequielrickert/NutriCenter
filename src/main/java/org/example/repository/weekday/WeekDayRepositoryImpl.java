package org.example.repository.weekday;

import org.example.model.history.MealType;
import org.example.model.history.WeekDay;
import org.example.model.recipe.Recipe;
import org.example.repository.weeklyhistory.WeeklyHistoryRepository;
import org.example.repository.weeklyhistory.WeeklyHistoryRepositoryImpl;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;

public class WeekDayRepositoryImpl implements WeekDayRepository{

    EntityManager entityManager;

    public WeekDayRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public WeekDay createWeekDay(DayOfWeek dayName) {
        entityManager.getTransaction().begin();
        WeekDay weekDay = new  WeekDay();
        weekDay.setDayName(dayName);
        entityManager.getTransaction().commit();
        return weekDay;
    }

    @Override
    public WeekDay readWeekDay(Long weekDayId) {
        entityManager.getTransaction().begin();
        WeekDay weekDay = entityManager.find(WeekDay.class, weekDayId);
        entityManager.getTransaction().commit();
        return weekDay;
    }

    @Override
    public void updateWeekDay(Long weekDayId, Recipe recipe, MealType mealType) {
        entityManager.getTransaction().begin();
        WeekDay weekDay = entityManager.find(WeekDay.class, weekDayId);
        switch (mealType) {
            case BREAKFAST:
                weekDay.setBreakfast(recipe);
                break;
            case LUNCH:
                weekDay.setLunch(recipe);
                break;
            case DINNER:
                weekDay.setDinner(recipe);
                break;
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteWeekDay(Long weekDayId) {
    }
}
