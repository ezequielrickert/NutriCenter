package org.example.model.roles;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

@Entity(name = "NUTRITIONIST")
public class Nutritionist {

    @Id
    @Expose
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long nutritionistId;

    @Expose
    @Column(nullable = false, unique = false)
    private String nutritionistName;

    @Expose
    @Column(nullable = false, unique = true)
    private String nutritionistEmail;

    @Expose
    @Column(nullable = false, unique = false)
    private String nutritionistPassword;

    @Expose
    @Column(nullable = false, unique = false)
    private String educationDiploma;

    @ManyToMany(mappedBy = "nutritionists")
    private List<Customer> customers;

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

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}