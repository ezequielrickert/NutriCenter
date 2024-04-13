package org.example.model;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.List;


@Entity(name = "ALLERGY")
public class Allergy {

    @Id
    @GeneratedValue(generator = "allergyGenerator", strategy = GenerationType.IDENTITY)
    private Long allergyId;

    @OneToMany(mappedBy = "allergy")
    private List<Ingredient> ingredients;

    @Column(nullable = false, unique = true)
    private String allergyName;

    @Column(nullable = false, unique = false)
    private String description;

    public Allergy(String name, String description) {
        this.allergyName = name;
        this.description = description;
    }

    public Allergy() {

    }

    public void setDescription(String description) {this.description = description; }

    public Long getAllergyId() {return this.allergyId; }

    public String getAllergyName() {return this.allergyName; }

    public String getDescription() {return this.description; }

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

