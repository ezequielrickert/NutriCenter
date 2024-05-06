package org.example.repository.stock;

import org.example.model.Ingredient;
import org.example.model.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import javax.persistence.EntityManager;
import java.util.List;

public class StockRepositoryImpl implements StockRepository{

    EntityManager entityManager;

    public StockRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createStock(Store store, Ingredient ingredient, int quantity, String brand) {
        entityManager.getTransaction().begin();
        Store managedStore = entityManager.find(Store.class, store.getStoreId());
        Ingredient managedIngredient = entityManager.find(Ingredient.class, ingredient.getIngredientId());
        Stock stock = new Stock();
        stock.setId(new StockId(managedStore.getStoreId(), managedIngredient.getIngredientId()));
        stock.setStore(managedStore);
        stock.setIngredient(managedIngredient);
        stock.setQuantity(quantity);
        stock.setBrand(brand);
        entityManager.persist(stock);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Stock> readStock(Long storeId) {
        entityManager.getTransaction().begin();
        List<Stock> stock = entityManager.createQuery("SELECT s FROM STOCK s WHERE s.id.storeId = :storeId", Stock.class)
                .setParameter("storeId", storeId)
                .getResultList();
        entityManager.getTransaction().commit();
        return stock;
    }


    @Override
    public void updateStock(StockId stockId, Ingredient ingredientId, int quantity, String brand) {
        entityManager.getTransaction().begin();
        Stock stock = entityManager.find(Stock.class, stockId);
        stock.setQuantity(quantity);
        stock.setBrand(brand);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteStock(Long storeId, Long ingredientId) {
        entityManager.getTransaction().begin();
        Stock stock = entityManager.find(Stock.class, new StockId(storeId, ingredientId));
        entityManager.remove(stock);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Store> getStoresByIngredient(Ingredient ingredient) {
        entityManager.getTransaction().begin();
        List<Store> stores = entityManager.createQuery(
                        "SELECT s.store FROM STOCK s WHERE s.ingredient = :ingredient", Store.class)
                .setParameter("ingredient", ingredient)
                .getResultList();
        entityManager.getTransaction().commit();
        return stores;
    }

}
