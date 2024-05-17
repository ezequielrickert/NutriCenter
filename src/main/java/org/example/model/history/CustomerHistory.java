package org.example.model.history;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerHistory {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long customerHistoryId;

    @OneToMany(mappedBy = "customerHistory", cascade = CascadeType.ALL)
    private List<Day> days;

    public CustomerHistory() {
    }

    public Long getCustomerHistoryId() {
        return customerHistoryId;
    }

    public List<Day> getDays() {
        return days;
    }
}