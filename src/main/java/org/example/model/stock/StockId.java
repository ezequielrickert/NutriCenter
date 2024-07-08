package org.example.model.stock;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class StockId implements Serializable {

    @Expose
    private Long storeId;

    @Expose
    private Long ingredientId;

    @Expose
    private String brandName;

    public StockId() {}

    public StockId(Long storeId, Long ingredientId, String brand) {
        this.storeId = storeId;
        this.ingredientId = ingredientId;
        this.brandName = brand;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getBrand() {
        return brandName;
    }

    public void setBrand(String brand) {
        this.brandName = brand;
    }
}