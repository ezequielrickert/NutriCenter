package org.example.repository.weekday;

import org.example.model.history.MealType;
import org.example.model.history.WeekDay;
import org.example.model.recipe.Recipe;

import java.time.DayOfWeek;

public interface WeekDayRepository {

    WeekDay createWeekDay(DayOfWeek dayName);

    WeekDay readWeekDay(Long weekDayId);

    void updateWeekDay(Long weekDayId, Recipe recipe, MealType mealType);

    void deleteWeekDay(Long weekDayId);

}
