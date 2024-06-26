package org.example.model.history;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.example.model.recipe.Recipe;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
public class Day {

    @Expose(serialize = true)
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long dayId;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private String dayName;

    @Expose(serialize = true)
    @ManyToOne
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_breakfast_recipeId"))
    public Recipe breakfast;

    @Expose(serialize = true)
    @ManyToOne
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_lunch_recipeId"))
    public Recipe lunch;

    @Expose(serialize = true)
    @ManyToOne
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_dinner_recipeId"))
    public Recipe dinner;


    @Column(nullable = false, unique = false)
    private LocalDate date;

    public Day() {
    }

    public Day(DayOfWeek dayName, LocalDate date) {
        this.dayName = dayName.name();
        this.date = date;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public Long getDayId() {
        return dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
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
