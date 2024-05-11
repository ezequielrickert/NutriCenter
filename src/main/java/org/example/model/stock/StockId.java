package org.example.model.stock;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class StockId implements Serializable {

    public StockId() {}

    public StockId(Long storeId, Long ingredientId) {
        this.storeId = storeId;
        this.ingredientId = ingredientId;
    }

    private Long storeId;
    private Long ingredientId;

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
}