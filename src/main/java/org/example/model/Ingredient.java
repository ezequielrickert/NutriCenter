package org.example.model;
import com.google.gson.Gson;

import javax.persistence.*;

@Entity(name = "INGREDIENT")
public class Ingredient {

    @Id
    @GeneratedValue(generator = "ingredientGen", strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @Column(nullable = false, unique = false)
    private String ingredientName;

    //this column has stored an Allergy instance not an id
    @ManyToOne
    @JoinColumn(name = "allergy_id", foreignKey = @ForeignKey(name = "fk_allergy_id"))
    private Allergy allergy;

    @Column(nullable = false, unique = false)
    private int protein;

    @Column(nullable = false, unique = false)
    private int sodium;

    @Column(nullable = false, unique = false)
    private int calories;

    @Column(nullable = false, unique = false)
    private int totalFat;

    @Column(nullable = false, unique = false)
    private int cholesterol;

    @Column(nullable = false, unique = false)
    private int totalCarbohydrate;


    public Ingredient(String ingredientName, Allergy allergy, int protein, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate) {
        this.ingredientName = ingredientName;
        this.allergy = allergy;
        this.protein = protein;
        this.sodium = sodium;
        this.calories = calories;
        this.totalFat = totalFat;
        this.cholesterol = cholesterol;
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public Ingredient() {

    }

    public void setAllergy(Allergy allergy) { this.allergy = allergy; }

    public void setProtein(int protein) { this.protein = protein; }

    public void setSodium(int sodium ) {
        this.sodium = sodium;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setTotalFat(int totalFat) { this.totalFat = totalFat; }

    public void setCholesterol(int cholesterol) {this.cholesterol = cholesterol; }

    public void setTotalCarbohydrate(int totalCarbohydrate) {this.totalCarbohydrate = totalCarbohydrate; }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Allergy getAllergy() {
        return allergy;
    }

    public int getProtein() { return protein; }

    public int getSodium() {
        return sodium;
    }

    public int getCalories() {
        return calories;
    }

    public int getTotalFat() { return totalFat; }

    public int getCholesterol() {return cholesterol; }

    public int getTotalCarbohydrate() {return totalCarbohydrate; }


    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}