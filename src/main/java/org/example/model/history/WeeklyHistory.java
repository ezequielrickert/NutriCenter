package org.example.model.history;

import org.example.model.roles.Customer;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Entity
public class WeeklyHistory {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long weeklyHistoryId;


    @OneToMany(mappedBy = "weeklyHistory", cascade = CascadeType.ALL)
    private List<WeekDay> days;

    @Column(nullable = false, unique = false)
    private DayOfWeek startDate;

    @Column(nullable = false, unique = false)
    private DayOfWeek endDate;

    @Column(nullable = false, unique = false)
    private String status;


    public WeeklyHistory() {
        this.startDate = DayOfWeek.MONDAY;
        this.endDate = DayOfWeek.SUNDAY;
        this.status = "pending";
    }

    public Long getWeeklyHistoryId() {
        return weeklyHistoryId;
    }


    public List<WeekDay> getDays() {
        return days;
    }

    public void setDays(List<WeekDay> days) {
        this.days = days;
    }


    public DayOfWeek getStartDate() {
        return startDate;
    }

    public DayOfWeek getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}