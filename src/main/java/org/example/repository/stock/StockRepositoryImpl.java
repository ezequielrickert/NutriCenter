package org.example.repository.stock;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.EntityTransaction;
import java.util.List;

public class StockRepositoryImpl implements StockRepository {

    private final EntityManager entityManager;

    public StockRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    @Override
    public void createStock(Store store, Ingredient ingredient, int quantity, String brand) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
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
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Stock> readStock(Long storeId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            List<Stock> stock = entityManager.createQuery("SELECT s FROM STOCK s WHERE s.id.storeId = :storeId", Stock.class)
                    .setParameter("storeId", storeId)
                    .getResultList();
            transaction.commit();
            return stock;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updateStock(StockId stockId, Ingredient ingredient, int quantity, String brand) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            Stock stock = entityManager.find(Stock.class, stockId);
            if (stock != null) {
                stock.setIngredient(ingredient);
                stock.setQuantity(quantity);
                stock.setBrand(brand);
                entityManager.merge(stock);
            } else {
                throw new RuntimeException("Stock not found for the given StockId.");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    @Override
    public void deleteStock(StockId stockId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            Stock stock = entityManager.find(Stock.class, stockId);
            if (stock != null) {
                entityManager.remove(stock);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Store> getStoresByIngredient(Ingredient ingredient) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            List<Store> stores = entityManager.createQuery(
                            "SELECT DISTINCT s.store FROM STOCK s WHERE s.ingredient = :ingredient", Store.class)
                    .setParameter("ingredient", ingredient)
                    .getResultList();
            transaction.commit();
            return stores;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Stock findStockAvoidStockId(StockId stockId, Store store, Ingredient ingredient, String brand) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            Stock stock = entityManager.createQuery(
                            "SELECT s FROM STOCK s WHERE s.id.storeId = :storeId AND s.id.ingredientId = :ingredientId AND s.id.brandName = :brand AND s.id != :stockId", Stock.class)
                    .setParameter("storeId", store.getStoreId())
                    .setParameter("ingredientId", ingredient.getIngredientId())
                    .setParameter("brand", brand)
                    .setParameter("stockId", stockId)
                    .getSingleResult();
            transaction.commit();
            return stock;
        } catch (NoResultException e) {
            if (transaction.isActive()) {
                transaction.commit();
            }
            return null;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Stock findStock(Store store, Ingredient ingredient, String brand) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            Stock stock = entityManager.createQuery(
                            "SELECT s FROM STOCK s WHERE s.id.storeId = :storeId AND s.id.ingredientId = :ingredientId AND s.id.brandName = :brand", Stock.class)
                    .setParameter("storeId", store.getStoreId())
                    .setParameter("ingredientId", ingredient.getIngredientId())
                    .setParameter("brand", brand)
                    .getSingleResult();
            transaction.commit();
            return stock;
        } catch (NoResultException e) {
            if (transaction.isActive()) {
                transaction.commit();
            }
            return null;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public StockId fetchStockId(Integer stockId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            beginTransaction();
            Stock stock = entityManager.find(Stock.class, stockId);
            transaction.commit();
            return stock.getId();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
