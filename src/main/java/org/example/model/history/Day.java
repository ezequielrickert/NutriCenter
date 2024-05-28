package org.example.model.history;

import com.google.gson.annotations.Expose;
import org.example.model.recipe.Recipe;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
public class Day {

    @Id
    @Expose
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long dayId;

    @ManyToOne
    @Expose
    @JoinColumn(name = "CustomerHistoryId")
    private CustomerHistory customerHistory;

    @Expose
    @Column(nullable = false, unique = false)
    private DayOfWeek dayName;

    @ManyToOne
    @Expose
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_breakfast_recipeId"))
    public Recipe breakfast;

    @ManyToOne
    @Expose
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_lunch_recipeId"))
    public Recipe lunch;

    @ManyToOne
    @Expose
    @JoinColumn(referencedColumnName = "recipeId", foreignKey = @ForeignKey(name = "FK_dinner_recipeId"))
    public Recipe dinner;

    @Column(nullable = false, unique = false)
    private LocalDate date;

    public Day() {
    }

    public Day(DayOfWeek dayName, LocalDate date, CustomerHistory customerHistory) {
        this.dayName = dayName;
        this.date = date;
        this.customerHistory = customerHistory;
    }

    public CustomerHistory getCustomerHistory() {
        return customerHistory;
    }

    public void setCustomerHistory(CustomerHistory customerHistory) {
        this.customerHistory = customerHistory;
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
