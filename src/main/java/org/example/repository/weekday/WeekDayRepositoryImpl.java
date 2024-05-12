package org.example.repository.weekday;

import org.example.model.history.MealType;
import org.example.model.history.WeekDay;
import org.example.model.recipe.Recipe;
import org.example.model.roles.Customer;
import org.example.repository.weeklyhistory.WeeklyHistoryRepository;
import org.example.repository.weeklyhistory.WeeklyHistoryRepositoryImpl;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

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
        LocalDateTime currentTime = LocalDateTime.now();
        WeekDay weekDay = entityManager.find(WeekDay.class, weekDayId);
        if(sameDayUpdate(currentTime, weekDay.getDayName())){
            updateMeal(recipe, mealType, weekDay);
        }
        else{
            weekDay.clearDays();
            updateMeal(recipe, mealType, weekDay);
        }
        lastUpdates.put(weekDay.getDayName(), currentTime);
        entityManager.getTransaction().commit();
    }

    private static void updateMeal(Recipe recipe, MealType mealType, WeekDay weekDay) {
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
    }

    @Override
    public void deleteWeekDay(Long weekDayId) {
        entityManager.getTransaction().begin();

        WeekDay day = entityManager.find(WeekDay.class, weekDayId);
        if (day != null) {
            entityManager.remove(day);
        }
        entityManager.getTransaction().commit();
    }

    // Map con día y ultimo update
    Map<DayOfWeek, LocalDateTime> lastUpdates = new HashMap<>(7);

    private boolean sameDayUpdate(LocalDateTime updateTime, DayOfWeek dayName){
        LocalDateTime lastUpdateTime = lastUpdates.get(dayName);
        if(lastUpdateTime != null){
            return ChronoUnit.HOURS.between(lastUpdateTime, updateTime) <= 24;
        }
        return false;
    }
}
