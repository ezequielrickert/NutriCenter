package org.example.service;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
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

    public void createStock(String storeName, Ingredient ingredientsId, int quantity, String brand) {
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        Store store = storeRepository.fetchStoreByName(storeName);
        stockRepository.createStock(store, ingredientsId, quantity, brand);
    }

    public void updateStock(String storeName, Ingredient ingredientsId, int quantity, String brand) {
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        Store store = storeRepository.fetchStoreByName(storeName);
        List<Stock> stock = stockRepository.readStock(storeId);
        for (Stock s : stock) {
            if (s.getIngredient().getIngredientId().equals(ingredientsId.getIngredientId())) {
                stockRepository.updateStock(s.getId(), ingredientsId, quantity, brand);
            }
        }
    }

    public void deleteStock(String storeName, Long ingredientId) {
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        stockRepository.deleteStock(storeId, ingredientId);
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
