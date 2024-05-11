package org.example.model.recipe;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity(name = "ALLERGY")
public class Allergy {

    @Expose(serialize = true)
    @Id
    @GeneratedValue(generator = "allergyGenerator", strategy = GenerationType.IDENTITY)
    private Long allergyId;

    @Expose(serialize = true)
    @Column(nullable = false, unique = true)
    private String allergyName;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private String description;

    public Allergy(String name, String description) {
        this.allergyName = name;
        this.description = description;
    }

    public Allergy() {

    }

    public void setDescription(String description) {this.description = description; }

    public void setAllergyName(String allergyName) {this.allergyName = allergyName;}

    public Long getAllergyId() {return this.allergyId; }

    public String getAllergyName() {return this.allergyName; }

    public String getDescription() {return this.description; }

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

