package org.example.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class DietDay {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long dayId;

    @Column(nullable = false, unique = false)
    private String dateName;

    @Column(nullable = false, unique = false)
    private ArrayList<ingredientId> breakfast;

    @Column(nullable = false, unique = false)
    private ArrayList<ingredientId> lunch;

    @Column(nullable = false, unique = false)
    private ArrayList<ingredientId> dinner;
}
