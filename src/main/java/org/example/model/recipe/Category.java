package org.example.model.recipe;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;

@Entity(name = "CATEGORY")
public class Category {

    @Expose(serialize = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose(serialize = true)
    @Column
    @Unique
    private String categoryName;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
