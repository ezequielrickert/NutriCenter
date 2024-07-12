package org.example.service;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;
import org.example.repository.ingredient.IngredientRepositoryImp;
import org.example.repository.stock.StockRepositoryImpl;
import org.example.repository.store.StoreRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

public class StockService {
    private final StockRepositoryImpl stockRepository;
    private final StoreRepositoryImpl storeRepository;
    private final IngredientRepositoryImp ingredientRepository;

    public StockService(EntityManager entityManager) {
        this.stockRepository = new StockRepositoryImpl(entityManager);
        this.storeRepository = new StoreRepositoryImpl(entityManager);
        this.ingredientRepository = new IngredientRepositoryImp(entityManager);
    }

    public void createStock(String storeName, Long ingredientId, int quantity, String brand) {
        Stock stock = new Stock();
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        stock.setId(new StockId(storeId, ingredientId, brand));
        stock.setStore(storeRepository.readStore(storeId));
        stock.setIngredient(ingredientRepository.readIngredient(ingredientId));
        stock.setQuantity(quantity);
        stockRepository.create(stock);
    }

    public List<Stock> readStock(String storeName) {
        return stockRepository.findByStoreName(storeName);
    }

    public void updateStock(StockId stockId, int quantity) {
        Stock stock = stockRepository.findById(stockId);
        if (stock != null) {
            stock.setQuantity(quantity);
            stockRepository.update(stock);
        }
    }

    public void deleteStock(StockId stockId) {
        Stock stock = stockRepository.findById(stockId);
        if (stock != null) {
            stockRepository.delete(stock);
        }
    }

    public boolean checkStockExists(String storeName, Long ingredientId, String brand) {
        return stockRepository.existsByStoreNameAndIngredientIdAndBrand(storeName, ingredientId, brand);
    }

    public boolean checkStockExistsForUpdate(StockId stockId, String storeName, Long ingredientId, String brand) {
        return stockRepository.existsByStoreNameAndIngredientIdAndBrandAndNotStockId(storeName, ingredientId, brand, stockId);
    }

    public List<Store> getStoresByIngredient(String ingredientName) {
        Ingredient ingredient = ingredientRepository.getIngredientByName(ingredientName);
        return stockRepository.getStoresByIngredient(ingredient);
    }
}
