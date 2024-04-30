package org.example.model.recipie;

import javax.persistence.*;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.List;

@Entity(name = "RECIPE")
public class Recipe {

    public Recipe() {}

    @Expose
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Expose
    @Column(nullable = false)
    private String recipeName;

    @Expose
    @Column
    private String recipeDescription;

    @ManyToMany
    @Expose
    @Column
    private List<Category> categoryList;

    @ManyToMany
    @Expose
    @Column
    private List<Ingredient> ingredientList;

    @Expose
    @Column
    private String recipeUsername;

    @Expose
    @Column
    private Boolean isPublic;

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setRecipeUsername(String recipeUsername) {
        this.recipeUsername = recipeUsername;
    }

    public String getRecipeUsername() {
        return recipeUsername;
    }

    public void setId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getId() {
        return recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public Long getRecipeId() {
        return recipeId;
    }
}
