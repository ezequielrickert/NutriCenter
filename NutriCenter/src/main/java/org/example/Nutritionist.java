package org.example;

import javax.persistence.*;

@Entity
public class Nutritionist {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long nutritionistId;

    @Column(nullable = false, unique = false)
    private String nutritionistName;

    @Column(nullable = false, unique = true)
    private String nutritionistEmail;

    @Column(nullable = false, unique = false)
    private String educationDiploma;

    public Nutritionist(String name, String mail, String diploma) {
        nutritionistName = name;
        nutritionistEmail = mail;
        educationDiploma = diploma;
    }

    public Nutritionist() {

    }
}