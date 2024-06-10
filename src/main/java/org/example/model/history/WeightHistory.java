package org.example.model.history;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class WeightHistory {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weightHistoryId;

    @Expose
    @Column
    private double weight;

    @Expose
    @Column
    private LocalDate date;

    public WeightHistory() {

    }

    public double getWeight() {
        return weight;
    }

    public WeightHistory(double weight) {
        this.weight = weight;
        this.date = LocalDate.now();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getWeightHistoryId() {
        return weightHistoryId;
    }
}
