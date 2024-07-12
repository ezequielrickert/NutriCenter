package org.example.repository.stock;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class StockRepositoryImpl {

    private final EntityManager entityManager;

    public StockRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Stock stock) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(stock);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace(); // Imprimir la excepción para depuración
            throw e;
        }
    }

    public List<Stock> findByStoreName(String storeName) {
        TypedQuery<Stock> query = entityManager.createQuery("SELECT s FROM STOCK s WHERE s.store.storeName = :storeName", Stock.class);
        query.setParameter("storeName", storeName);
        return query.getResultList();
    }

    public Stock findById(StockId stockId) {
        return entityManager.find(Stock.class, stockId);
    }

    public void update(Stock stock) {
        entityManager.getTransaction().begin();
        entityManager.merge(stock);
        entityManager.getTransaction().commit();
    }

    public void delete(Stock stock) {
        entityManager.getTransaction().begin();
        entityManager.remove(stock);
        entityManager.getTransaction().commit();
    }

    public boolean existsByStoreNameAndIngredientIdAndBrand(String storeName, Long ingredientId, String brand) {
        TypedQuery<Stock> query = entityManager.createQuery(
                "SELECT s FROM STOCK s WHERE s.store.storeName = :storeName AND s.ingredient.id = :ingredientId AND s.id.brand = :brand", Stock.class);
        query.setParameter("storeName", storeName);
        query.setParameter("ingredientId", ingredientId);
        query.setParameter("brand", brand);
        return !query.getResultList().isEmpty();
    }

    public boolean existsByStoreNameAndIngredientIdAndBrandAndNotStockId(String storeName, Long ingredientId, String brand, StockId stockId) {
        TypedQuery<Stock> query = entityManager.createQuery(
                "SELECT s FROM STOCK s WHERE s.store.storeName = :storeName AND s.ingredient.id = :ingredientId AND s.id.brand = :brand AND s.id != :stockId", Stock.class);
        query.setParameter("storeName", storeName);
        query.setParameter("ingredientId", ingredientId);
        query.setParameter("brand", brand);
        query.setParameter("stockId", stockId);
        return !query.getResultList().isEmpty();
    }

    public List<Store> getStoresByIngredient(Ingredient ingredient) {
        entityManager.getTransaction().begin();
        List<Store> stores = entityManager.createQuery(
                        "SELECT DISTINCT s.store FROM STOCK s WHERE s.ingredient = :ingredient", Store.class)
                .setParameter("ingredient", ingredient)
                .getResultList();
        entityManager.getTransaction().commit();
        return stores;
    }
}
