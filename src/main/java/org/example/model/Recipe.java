package org.example.model;

import javax.persistence.*;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.common.aliasing.qual.Unique;
import org.example.model.Category;
import org.example.model.Ingredient;
import org.hibernate.annotations.Columns;

import java.util.List;

@Entity(name = "RECIPE")
public class Recipe {

    public Recipe() {}

    @Expose
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String username;

    @Expose
    @Column
    private Boolean isPublic;

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
        return id;
    }
}
