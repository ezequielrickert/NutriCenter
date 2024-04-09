package org.example.model;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity(name = "NUTRITIONIST")
public class Nutritionist {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long nutritionistId;

    @Column(nullable = false, unique = false)
    private String nutritionistName;

    @Column(nullable = false, unique = true)
    private String nutritionistEmail;

    @Column(nullable = false, unique = false)
    private String nutritionistPassword;

    @Column(nullable = false, unique = false)
    private String educationDiploma;

    public Nutritionist(String name, String mail, String password, String diploma) {
        nutritionistName = name;
        nutritionistEmail = mail;
        nutritionistPassword = password;
        educationDiploma = diploma;
    }

    public void setNutritionistName(String nutritionistName) {
        this.nutritionistName = nutritionistName;
    }

    public void setNutritionistEmail(String nutritionistEmail) {
        this.nutritionistEmail = nutritionistEmail;
    }

    public void setNutritionistPassword(String password) {
        this.nutritionistPassword = password;
    }

    public void setEducationDiploma(String diploma) {
        this.educationDiploma = diploma;
    }

    public Long getNutritionistId() {
        return nutritionistId;
    }

    public String getNutritionistName() {
        return nutritionistName;
    }

    public String getNutritionistEmail() {
        return nutritionistEmail;
    }

    public String getNutritionistPassword() {
        return nutritionistPassword;
    }

    public String getEducationDiploma() {
        return educationDiploma;
    }

    public Nutritionist() {

    }

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}