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
import java.util.ArrayList;
import java.util.List;

public class StockService {

    List<CartElement> cart = new ArrayList<>();
    private final StockRepositoryImpl stockRepository;
    private final StoreRepositoryImpl storeRepository;
    private final IngredientRepositoryImp ingredientRepository;

    public StockService(EntityManager entityManager) {
        this.stockRepository = new StockRepositoryImpl(entityManager);
        this.storeRepository = new StoreRepositoryImpl(entityManager);
        this.ingredientRepository = new IngredientRepositoryImp(entityManager);
    }

    public void createStock(String storeName, Long ingredientId, int quantity, String brand, double price) {
        Stock stock = new Stock();
        Long storeId = storeRepository.fetchStoreByName(storeName).getStoreId();
        stock.setId(new StockId(storeId, ingredientId, brand));
        stock.setStore(storeRepository.readStore(storeId));
        stock.setIngredient(ingredientRepository.readIngredient(ingredientId));
        stock.setQuantity(quantity);
        stock.setPrice(price);
        stockRepository.create(stock);
    }

    public List<Stock> readStock(String storeName) {
        return stockRepository.findByStoreName(storeName);
    }

    public void updateStock(StockId stockId, int quantity, double price) {
        Stock stock = stockRepository.findById(stockId);
        if (stock != null) {
            stock.setQuantity(quantity);
            stock.setPrice(price);
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

    public List<Store> getStoresByIngredient(String ingredientName) {
        Ingredient ingredient = ingredientRepository.getIngredientByName(ingredientName);
        return stockRepository.getStoresByIngredient(ingredient);
    }

    public void purchase(String storeName, String ingredientName, int quantity) {
        Ingredient ingredient = ingredientRepository.getIngredientByName(ingredientName);
        List<Stock> stock = stockRepository.findByStoreName(storeName);
        for (Stock product : stock) {
            int remain = product.getQuantity() - quantity;
            boolean matchIngredient = product.getIngredient().getIngredientId().equals(ingredient.getIngredientId());
            if (matchIngredient && remain >= 0) {
                product.setQuantity(remain);
                stockRepository.update(product);
            }
        }
    }

    public void addToCart(String storeName, String ingredientName, int quantity){
        CartElement element = new CartElement(storeName, ingredientName, quantity);
        cart.add(element);
    }

    public void purchaseCart(){
        for(CartElement element : cart){
            purchase(element.storeName, element.ingredientName, element.quantity);
        }
    }

    public void emptyCart(){
        cart.clear();
    }


    private class CartElement {
        private final String storeName;
        private final String ingredientName;
        private final int quantity;

        public CartElement(String storeName, String ingredientName, int quantity) {
            this.storeName = storeName;
            this.ingredientName = ingredientName;
            this.quantity = quantity;
        }

        public String getStoreName() {
            return storeName;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}