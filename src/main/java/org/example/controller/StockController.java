package org.example.controller;

import org.example.model.Ingredient;
import org.example.model.Store;
import org.example.model.stock.Stock;
import org.example.service.StockService;

import javax.persistence.EntityManager;
import java.util.List;

public class StockController {

    EntityManager entityManager;
    StockService stockService;

    public StockController(EntityManager entityManager) {
        this.stockService = new StockService(entityManager);
    }

    public void createStock(String storeName, Ingredient ingredientsId, int quantity, String brand) {
        stockService.createStock(storeName, ingredientsId, quantity, brand);
    }

    public List<Stock> readStock(String storeName) {
        return stockService.readStock(storeName);
    }

    public void updateStock(String storeName, Ingredient ingredientsId, int quantity, String brand) {
        stockService.updateStock(storeName, ingredientsId, quantity, brand);
    }

    public void deleteStock(String stockId, Long ingredientId) {
        stockService.deleteStock(stockId, ingredientId);
    }

    public List<Store> getStoresByIngredient(String ingredientName) {
        return stockService.getStoresByIngredient(ingredientName);
    }
}
