package org.example.service;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;
import org.example.repository.ingredient.IngredientRepositoryImp;
import org.example.repository.stock.StockRepository;
import org.example.repository.stock.StockRepositoryImpl;
import org.example.repository.store.StoreRepository;
import org.example.repository.store.StoreRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public class StockService {

    StockRepository stockRepository;
    StoreRepository storeRepository;
    IngredientRepositoryImp ingredientRepository;

    public StockService(EntityManager entityManager) {
        this.stockRepository = new StockRepositoryImpl(entityManager);
        this.storeRepository = new StoreRepositoryImpl(entityManager);
        this.ingredientRepository = new IngredientRepositoryImp(entityManager);
    }

    public void createStock(String storeName, Ingredient ingredient, int quantity, String brand) {
        Store store = storeRepository.fetchStoreByName(storeName);

        Stock existingStock = stockRepository.findStock(store, ingredient, brand);

        if (existingStock != null) {
            throw new RuntimeException("El stock ya existe para esta combinación de tienda, ingrediente y marca.");
        } else {
            stockRepository.createStock(store, ingredient, quantity, brand);
        }
    }

    public void updateStock(StockId stockId, String storeName, Ingredient ingredient, int quantity, String brand) {
        Store store = storeRepository.fetchStoreByName(storeName);

        try {
            Stock existingStock = stockRepository.findStockAvoidStockId(stockId, store, ingredient, brand);

            if (existingStock != null) {
                throw new RuntimeException("El stock ya existe para esta combinación de tienda, ingrediente y marca.");
            } else {
                stockRepository.updateStock(stockId, ingredient, quantity, brand);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el stock: " + e.getMessage());
        }
    }

    public void deleteStock(StockId stockId) {
        stockRepository.deleteStock(stockId);
    }

    public List<Stock> readStock(String storeName) {
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        return stockRepository.readStock(storeId);
    }

    public List<Store> getStoresByIngredient(String ingredientName) {
        Ingredient ingredient = ingredientRepository.getIngredientByName(ingredientName);
        return stockRepository.getStoresByIngredient(ingredient);
    }
}