package org.example.model.stock;

import org.example.model.Ingredient;
import org.example.model.Store;

import javax.persistence.*;
import java.util.List;

@Entity(name = "STOCK")
public class Stock {

    @EmbeddedId
    private StockId id;

    @ManyToOne
    @MapsId("storeId")
    private Store store;

    @ManyToOne
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private int quantity;

    @Column
    private String brand;

    public StockId getId() {
        return id;
    }

    public void setId(StockId id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    // existing fields, getters and setters...
}