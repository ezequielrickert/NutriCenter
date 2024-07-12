package org.example.model.stock;

import com.google.gson.annotations.Expose;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;

import javax.persistence.*;

@Entity(name = "STOCK")
public class Stock {

    @Expose
    @EmbeddedId
    private StockId id;

    @Expose
    @ManyToOne
    @MapsId("storeId")
    private Store store;

    @Expose
    @ManyToOne
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Expose
    @Column(nullable = false)
    private int quantity;

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
}
