package org.example.model.recipie;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.example.model.stock.Stock;

import javax.persistence.*;
import java.util.List;

@Entity(name = "INGREDIENT")
public class Ingredient {

    @Expose(serialize = true)
    @Id
    @GeneratedValue(generator = "ingredientGen", strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @Expose(serialize = true)
    @Column(nullable = false, unique = true)
    private String ingredientName;

    //this column has stored an Allergy instance not an id
    @Expose(serialize = true)
    @ManyToOne
    @JoinColumn(name = "allergyId", foreignKey = @ForeignKey(name = "fk_allergyId"))
    private Allergy allergy;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private int proteins;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private int sodium;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private int calories;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private int totalFat;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private int cholesterol;

    @Expose(serialize = true)
    @Column(nullable = false, unique = false)
    private int totalCarbohydrate;

    public Ingredient(String ingredientName, Allergy allergy, int proteins, int sodium, int calories, int totalFat, int cholesterol, int totalCarbohydrate) {
        this.ingredientName = ingredientName;
        this.allergy = allergy;
        this.proteins = proteins;
        this.sodium = sodium;
        this.calories = calories;
        this.totalFat = totalFat;
        this.cholesterol = cholesterol;
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public Ingredient() {

    }

    public void setIngredientId(Long ingredientId) {this.ingredientId = ingredientId;}

    public void setIngredientName(String ingredientName) {this.ingredientName = ingredientName;}

    public void setAllergy(Allergy allergy) { this.allergy = allergy; }

    public void setProteins(int proteins) { this.proteins = proteins; }

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

    public int getProteins() { return proteins; }

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
