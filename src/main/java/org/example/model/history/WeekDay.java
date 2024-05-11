package org.example.model.history;

import org.example.model.recipe.Recipe;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
public class WeekDay {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long weekDayId;

    @ManyToOne
    @JoinColumn(name = "WeeklyHistoryId")
    private WeeklyHistory weeklyHistory;

    @Column(nullable = false, unique = false)
    private DayOfWeek dayName;


    @ManyToOne
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_breakfast_recipeId"))
    public Recipe breakfast;

    @ManyToOne
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_lunch_recipeId"))
    public Recipe lunch;

    @ManyToOne
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_dinner_recipeId"))
    public Recipe dinner;


    public WeekDay() {

    }


    public WeeklyHistory getWeeklyHistory() {
        return weeklyHistory;
    }

    public void setWeeklyHistory(WeeklyHistory weeklyHistory) {
        this.weeklyHistory = weeklyHistory;
    }

    public WeekDay(DayOfWeek dayName) {
        this.dayName = dayName;
    }

    public Long getWeekDayId() {
        return weekDayId;
    }

    public DayOfWeek getDayName() {
        return dayName;
    }

    public void setDayName(DayOfWeek dayName) {
        this.dayName = dayName;
    }

    public void setBreakfast(Recipe recipe) {
        this.breakfast = recipe;
    }

    public void setLunch(Recipe recipe) {
        this.lunch = recipe;
    }

    public void setDinner(Recipe recipe) {
        this.dinner = recipe;
    }

    public Recipe getBreakfast() {
        return breakfast;
    }

    public Recipe getLunch() {
        return lunch;
    }

    public Recipe getDinner() {
        return dinner;
    }
}
