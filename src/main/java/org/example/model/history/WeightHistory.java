package org.example.model.history;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class WeightHistory {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weightHistoryId;

    @Column
    private double weight;

    @Column
    private LocalDate date;

    public double getWeight() {
        return weight;
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
