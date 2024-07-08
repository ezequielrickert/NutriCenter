package org.example.service;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
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

        Stock existingStock = stockRepository.findStockByStoreAndIngredientAndBrand(store, ingredient, brand);

        if (existingStock != null) {
            throw new RuntimeException("El stock ya existe para esta combinación de tienda, ingrediente y marca.");
        } else {
            stockRepository.createStock(store, ingredient, quantity, brand);
        }
    }

    public void updateStock(String storeName, Ingredient ingredient, int quantity, String brand) {
//        Store store = storeRepository.fetchStoreByName(storeName);
//
//        Stock existingStock = stockRepository.findStockByStoreAndIngredientAndBrand(store, ingredient, brand);
//
//        if (existingStock != null) {
//            throw new RuntimeException("El stock ya existe para esta combinación de tienda, ingrediente y marca.");
//        } else {
//            StockId stockId =
//            stockRepository.updateStock(stockId, ingredient, quantity, brand);
//        }
    }

    public void deleteStock(String storeName, Long ingredientId, String brand) {
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        stockRepository.deleteStock(storeId, ingredientId, brand);
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