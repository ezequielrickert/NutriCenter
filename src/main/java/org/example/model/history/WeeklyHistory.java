package org.example.model.history;

import org.example.model.roles.Customer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class WeeklyHistory {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long weeklyHistoryId;

    @OneToOne(mappedBy = "weeklyHistory")
    private Customer customer;

    @OneToMany(mappedBy = "weeklyHistory", cascade = CascadeType.ALL)
    private List<WeekDay> days;

    @Column(nullable = false, unique = false)
    private LocalDate startDate;

    @Column(nullable = false, unique = false)
    private LocalDate endDate;

    @Column(nullable = false, unique = false)
    private String status;


    public WeeklyHistory() {

    }

    public WeeklyHistory(Long clientId, LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "pending";
    }

    public Long getWeeklyHistoryId() {
        return weeklyHistoryId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<WeekDay> getDays() {
        return days;
    }

    public void setDays(List<WeekDay> days) {
        this.days = days;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}