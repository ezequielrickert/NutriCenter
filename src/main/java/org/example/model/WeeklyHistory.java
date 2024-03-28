package org.example.model;

import javax.persistence.*;

@Entity
public class WeeklyHistory {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long weeklyHistoryId;

    @Column(nullable = false, unique = false)
    private Long clientId;

    @Column(nullable = false, unique = false)
    private int startDate;

    @Column(nullable = false, unique = false)
    private int endDate;

    @Column(nullable = false, unique = false)
    private String status;

    @Column(nullable = false, unique = false)
    private int dayId;
}
