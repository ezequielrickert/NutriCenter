package org.example.model.history;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerHistory {
    @Id
    @Expose
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long customerHistoryId;

    @OneToMany
    @Expose
    private List<Day> days;

    public CustomerHistory() {
    }

    public Long getCustomerHistoryId() {
        return customerHistoryId;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}