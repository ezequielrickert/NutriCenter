package org.example.repository.stock;


import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import java.util.List;

public interface StockRepository {

    void createStock(Store storeId, Ingredient ingredientId, int quantity, String brand, double price);

    List<Stock> readStock(Long storeId);

    void updateStock(StockId stockId, Ingredient ingredientId, int quantity, String brand, double price);

    void deleteStock(Long storeId, Long ingredientId) ;

    List<Store> getStoresByIngredient(Ingredient ingredient);


}