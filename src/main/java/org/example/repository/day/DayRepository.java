package org.example.repository.day;

import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;
import org.example.model.history.MealType;
import org.example.model.recipe.Recipe;

import java.time.DayOfWeek;

public interface DayRepository {

    Day createDay(DayOfWeek dayName);

    void readDay(Long weekDayId);

    void updateDay(Long weekDayId, Recipe recipe, MealType mealType);

    void deleteDay(Long weekDayId);

}
