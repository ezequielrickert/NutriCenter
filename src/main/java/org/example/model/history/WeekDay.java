package org.example.model.history;

import org.example.model.recipie.Recipe;

import javax.persistence.*;

@Entity
public class WeekDay {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long weekDayId;

    @ManyToOne
    @JoinColumn(name = "WeeklyHistoryId")
    private WeeklyHistory weeklyHistory;

    @Column(nullable = false, unique = false)
    private String dayName;

    /*
    @ManyToOne
    @JoinColumn(name = "recipeId", foreignKey = @ForeignKey(name = "FK_weekDay_recipeId"))
    public Recipe breakfast;

    @ManyToOne
    @JoinColumn(name = "recipeId", foreignKey = @ForeignKey(name = "FK_weekDay_recipeId"))
    public Recipe lunch;

    @ManyToOne
    @JoinColumn(name = "recipeId", foreignKey = @ForeignKey(name = "FK_weekDay_recipeId"))
    public Recipe dinner;
     */

    public WeekDay() {

    }


    public WeeklyHistory getWeeklyHistory() {
        return weeklyHistory;
    }

    public void setWeeklyHistory(WeeklyHistory weeklyHistory) {
        this.weeklyHistory = weeklyHistory;
    }

    public WeekDay(String dayName) {
        this.dayName = dayName;
    }

    public Long getWeekDayId() {
        return weekDayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
}
