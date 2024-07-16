package org.example.injection;

import org.example.model.recipe.Ingredient;
import org.example.model.roles.Store;
import org.example.model.stock.Stock;
import org.example.model.stock.StockId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class StockDataInjection {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Retrieve Store instances from the database
            Store store1 = em.createQuery("SELECT s FROM STORE s WHERE s.storeName = :name", Store.class)
                    .setParameter("name", "HealthyFoods")
                    .getSingleResult();
            Store store2 = em.createQuery("SELECT s FROM STORE s WHERE s.storeName = :name", Store.class)
                    .setParameter("name", "GreenGroceries")
                    .getSingleResult();
            Store store3 = em.createQuery("SELECT s FROM STORE s WHERE s.storeName = :name", Store.class)
                    .setParameter("name", "OrganicPicks")
                    .getSingleResult();
            Store store4 = em.createQuery("SELECT s FROM STORE s WHERE s.storeName = :name", Store.class)
                    .setParameter("name", "FarmFresh")
                    .getSingleResult();
            Store store5 = em.createQuery("SELECT s FROM STORE s WHERE s.storeName = :name", Store.class)
                    .setParameter("name", "VeganDelights")
                    .getSingleResult();

            // Retrieve Ingredient instances from the database
            Ingredient avocado = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Avocado")
                    .getSingleResult();
            Ingredient broccoli = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Broccoli")
                    .getSingleResult();
            Ingredient carrot = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Carrot")
                    .getSingleResult();
            Ingredient chickenBreast = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Chicken Breast")
                    .getSingleResult();
            Ingredient almond = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Almond")
                    .getSingleResult();
            Ingredient salmon = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Salmon")
                    .getSingleResult();
            Ingredient milk = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Milk")
                    .getSingleResult();
            Ingredient peanutButter = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Peanut Butter")
                    .getSingleResult();
            Ingredient soySauce = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Soy Sauce")
                    .getSingleResult();
            Ingredient egg = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Egg")
                    .getSingleResult();
            Ingredient shrimp = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Shrimp")
                    .getSingleResult();
            Ingredient bread = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Bread")
                    .getSingleResult();
            Ingredient cheddarCheese = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Cheddar Cheese")
                    .getSingleResult();
            Ingredient tuna = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Tuna")
                    .getSingleResult();
            Ingredient apple = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Apple")
                    .getSingleResult();
            Ingredient quinoa = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Quinoa")
                    .getSingleResult();
            Ingredient yogurt = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Yogurt")
                    .getSingleResult();
            Ingredient cashew = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Cashew")
                    .getSingleResult();
            Ingredient lentils = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Lentils")
                    .getSingleResult();
            Ingredient oats = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Oats")
                    .getSingleResult();

            // Create and persist Stock instances
            Stock stock1 = new Stock();
            StockId stockId1 = new StockId(store1.getStoreId(), avocado.getIngredientId(), "ExampleBrand");
            stock1.setId(stockId1);
            stock1.setStore(store1);
            stock1.setIngredient(avocado);
            stock1.setQuantity(100); // Example quantity
            stock1.setPrice(2000.5);    // Example price
            em.persist(stock1);

            Stock stock2 = new Stock();
            StockId stockId2 = new StockId(store2.getStoreId(), broccoli.getIngredientId(), "ExampleBrand");
            stock2.setId(stockId2);
            stock2.setStore(store2);
            stock2.setIngredient(broccoli);
            stock2.setQuantity(50);  // Example quantity
            stock2.setPrice(3500.0);    // Example price
            em.persist(stock2);

            Stock stock3 = new Stock();
            StockId stockId3 = new StockId(store3.getStoreId(), carrot.getIngredientId(), "ExampleBrand");
            stock3.setId(stockId3);
            stock3.setStore(store3);
            stock3.setIngredient(carrot);
            stock3.setQuantity(75);  // Example quantity
            stock3.setPrice(1800);    // Example price
            em.persist(stock3);

            Stock stock4 = new Stock();
            StockId stockId4 = new StockId(store4.getStoreId(), chickenBreast.getIngredientId(), "ExampleBrand");
            stock4.setId(stockId4);
            stock4.setStore(store4);
            stock4.setIngredient(chickenBreast);
            stock4.setQuantity(120); // Example quantity
            stock4.setPrice(550.5);    // Example price
            em.persist(stock4);

            Stock stock5 = new Stock();
            StockId stockId5 = new StockId(store5.getStoreId(), almond.getIngredientId(), "ExampleBrand");
            stock5.setId(stockId5);
            stock5.setStore(store5);
            stock5.setIngredient(almond);
            stock5.setQuantity(200); // Example quantity
            stock5.setPrice(4200.0);    // Example price
            em.persist(stock5);

            // Commit the transaction
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            emf.close();
        }
    }
}
