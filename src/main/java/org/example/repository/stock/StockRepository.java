package org.example.repository.stock;

import org.example.model.Ingredient;
import org.example.model.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import java.util.List;

public interface StockRepository {

    void createStock(Store storeId, Ingredient ingredientId, int quantity, String brand);

    List<Stock> readStock(Long storeId);

    void updateStock(StockId stockId, Ingredient ingredientId, int quantity, String brand);

    void deleteStock(Long storeId, Long ingredientId) ;

    List<Store> getStoresByIngredient(Ingredient ingredient);
}
