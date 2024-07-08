package org.example.repository.stock;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class StockRepositoryImpl implements StockRepository {

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
        StockId stockId = new StockId(managedStore.getStoreId(), managedIngredient.getIngredientId(), brand);
        stock.setId(stockId);
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
    public void updateStock(StockId stockId, Ingredient ingredient, int quantity, String brand) {
        entityManager.getTransaction().begin();
        Stock stock = entityManager.find(Stock.class, stockId);
        stock.setQuantity(quantity);
        stock.setBrand(brand);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteStock(Long storeId, Long ingredientId, String brand) {
        entityManager.getTransaction().begin();
        StockId stockId = new StockId(storeId, ingredientId, brand);
        Stock stock = entityManager.find(Stock.class, stockId);
        entityManager.remove(stock);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Store> getStoresByIngredient(Ingredient ingredient) {
        entityManager.getTransaction().begin();
        List<Store> stores = entityManager.createQuery(
                        "SELECT DISTINCT s.store FROM STOCK s WHERE s.ingredient = :ingredient", Store.class)
                .setParameter("ingredient", ingredient)
                .getResultList();
        entityManager.getTransaction().commit();
        return stores;
    }

    public Stock findStockByStoreAndIngredientAndBrand(Store store, Ingredient ingredient, String brand) {
        entityManager.getTransaction().begin();
        try {
            Stock stock = entityManager.createQuery(
                            "SELECT s FROM STOCK s WHERE s.store = :store AND s.ingredient = :ingredient AND s.id.brandName = :brand",
                            Stock.class)
                    .setParameter("store", store)
                    .setParameter("ingredient", ingredient)
                    .setParameter("brand", brand)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return stock;
        } catch (
                NoResultException e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }
}